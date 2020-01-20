use crate::{event::Event, UserId};
use maplit::hashset;
use std::{
    collections::{HashMap, HashSet},
    hash::BuildHasher,
    io::Write,
    sync::{mpsc::Receiver, Arc, Mutex},
    thread::JoinHandle,
};

pub fn process_events<S, H>(
    events: Receiver<Event>,
    user_streams: Arc<Mutex<HashMap<UserId, S, H>>>,
) -> JoinHandle<()>
where
    S: Write + Send + 'static,
    H: BuildHasher + Send + 'static,
{
    use std::thread;

    thread::spawn(move || {
        log::debug!("Event processor started");
        for event in events {
            let mut followers = HashMap::<UserId, HashSet<UserId>>::new();
            let mut blocked = HashMap::<UserId, HashSet<UserId>>::new();
            log::trace!("Processing {:?}", event);
            process_event(&event, &mut followers, &mut blocked);
            if let Some(stream) = user_streams
                .lock()
                .expect("failed to acquire user lock")
                .get_mut(&event.user_id)
            {
                log::trace!("Forwarding to user {:?}", event);
                writeln!(stream, "{}", event).expect("failed to write event to user stream");
                // Transmit the event right away without waiting for the buffer to fill.
                stream.flush().expect("failed to flush user stream");
            } else {
                log::trace!("User not connected, discarding {:?}", event);
            }
        }
    })
}

#[derive(PartialEq, Debug)]
enum EventTarget<'a> {
    Users(&'a HashSet<UserId>),
    User(UserId),
    NoOne,
}

/// Processes an event returning IDs of users that should receive it.
fn process_event<'a>(
    event: &Event,
    followers: &'a mut HashMap<UserId, HashSet<UserId>>,
    blocked: &mut HashMap<UserId, HashSet<UserId>>,
) -> EventTarget<'a> {
    use crate::event::Action::*;
    use std::collections::hash_map::Entry::*;

    match event.action {
        Follow(followed_user) => match followers.entry(event.user_id) {
            Occupied(mut entry) => {
                if entry.get_mut().insert(followed_user) {
                    EventTarget::User(followed_user)
                } else {
                    EventTarget::NoOne
                }
            }
            Vacant(entry) => {
                entry.insert(hashset! {followed_user});
                EventTarget::User(followed_user)
            }
        },
        Unfollow(unfollowed_user) => {
            followers.entry(event.user_id).and_modify(|e| {
                e.remove(&unfollowed_user);
            });
            EventTarget::NoOne
        }
        StatusUpdate(ref _message) => followers
            .get(&event.user_id)
            .map(|fs| EventTarget::Users(fs))
            .unwrap_or(EventTarget::NoOne),
        PrivateMessage(recipient, ref _message) => EventTarget::User(recipient),
        Block(blocked_user) => {
            blocked.entry(event.user_id)
                .and_modify(|e| {e.insert(blocked_user);})
                .or_insert(hashset!{blocked_user});
            EventTarget::NoOne
        },
    }
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::{event::Action, Result};

    #[test]
    fn forward_event() -> Result<()> {
        use crate::event::Event;
        use std::{io::BufWriter, sync::mpsc};

        let (sender, receiver) = mpsc::channel();
        let user_streams = Arc::new(Mutex::new(HashMap::<UserId, BufWriter<Vec<u8>>>::new()));
        user_streams
            .lock()
            .unwrap()
            .insert(42, BufWriter::new(Vec::new()));
        let handle = process_events(receiver, user_streams.clone());
        sender.send(Event {
            seq: 5,
            user_id: 42,
            action: Action::StatusUpdate("hello".to_string()),
        })?;
        // close the channel after sending the event
        drop(sender);

        // TODO: Replace with a timed retry.
        std::thread::sleep(std::time::Duration::from_secs(1));
        let user_stream = user_streams.lock().unwrap();
        let message = String::from_utf8(user_stream.get(&42).unwrap().get_ref().clone())?;
        assert_eq!("5/S/42/hello\n", message);

        // wait for the processor to finish and propagate panic from the processor thread
        handle.join().unwrap();
        Ok(())
    }

    #[test]
    fn drop_event_if_user_offline() -> Result<()> {
        use crate::event::Event;
        use std::{io::BufWriter, sync::mpsc};

        let (sender, receiver) = mpsc::channel();
        let user_streams = Arc::new(Mutex::new(HashMap::<UserId, BufWriter<Vec<u8>>>::new()));
        let handle = process_events(receiver, user_streams.clone());
        sender.send(Event {
            seq: 5,
            user_id: 42,
            action: Action::StatusUpdate("hello".to_string()),
        })?;
        // close the channel after sending the event
        drop(sender);

        // wait for the processor to finish and propagate panic from the processor thread
        handle.join().unwrap();
        Ok(())
    }

    #[test]
    fn process_follow() {
        let mut followers = HashMap::new();
        let mut blocked = HashMap::new();

        assert_eq!(
            EventTarget::User(7),
            process_event(
                &Event {
                    seq: 1,
                    user_id: 5,
                    action: Action::Follow(7),
                },
                &mut followers,
                &mut blocked
            )
        );
        assert_eq!(Some(&hashset! {7}), followers.get(&5));
    }

    #[test]
    fn process_follow_if_already_following() {
        let mut followers = HashMap::new();
        followers.insert(5, hashset! {7});
        let mut blocked = HashMap::new();

        assert_eq!(
            EventTarget::NoOne,
            process_event(
                &Event {
                    seq: 1,
                    user_id: 5,
                    action: Action::Follow(7),
                },
                &mut followers,
                &mut blocked
            )
        );
        assert_eq!(Some(&hashset! {7}), followers.get(&5));
    }

    #[test]
    fn process_follow_second_user() {
        let mut followers = HashMap::new();
        followers.insert(5, hashset! {7});
        let mut blocked = HashMap::new();

        assert_eq!(
            EventTarget::User(9),
            process_event(
                &Event {
                    seq: 1,
                    user_id: 5,
                    action: Action::Follow(9),
                },
                &mut followers,
                &mut blocked
            )
        );
        assert_eq!(Some(&hashset! {7, 9}), followers.get(&5));
    }

    #[test]
    fn process_unfollow() {
        let mut followers = HashMap::new();
        followers.insert(3, hashset![9]);
        let mut blocked = HashMap::new();

        assert_eq!(
            EventTarget::NoOne,
            process_event(
                &Event {
                    seq: 2,
                    user_id: 3,
                    action: Action::Unfollow(9),
                },
                &mut followers,
                &mut blocked
            )
        );
        assert_eq!(Some(&HashSet::new()), followers.get(&3));
    }

    #[test]
    fn process_unfollow_if_not_following() {
        let mut followers = HashMap::new();
        let mut blocked = HashMap::new();

        assert_eq!(
            EventTarget::NoOne,
            process_event(
                &Event {
                    seq: 2,
                    user_id: 3,
                    action: Action::Unfollow(9),
                },
                &mut followers,
                &mut blocked
            )
        );
    }

    #[test]
    fn process_status_update() {
        let mut followers = HashMap::new();
        followers.insert(3, hashset! {2, 5});
        let mut blocked = HashMap::new();

        assert_eq!(
            EventTarget::Users(&hashset! {2, 5}),
            process_event(
                &Event {
                    seq: 9,
                    user_id: 3,
                    action: Action::StatusUpdate("hello".to_string()),
                },
                &mut followers,
                &mut blocked
            )
        );
    }

    #[test]
    fn process_status_update_if_no_followers() {
        let mut followers = HashMap::new();
        let mut blocked = HashMap::new();

        assert_eq!(
            EventTarget::NoOne,
            process_event(
                &Event {
                    seq: 9,
                    user_id: 3,
                    action: Action::StatusUpdate("hello".to_string()),
                },
                &mut followers,
                &mut blocked
            )
        );
    }

    #[test]
    fn process_private_message() {
        let mut followers = HashMap::new();
        let mut blocked = HashMap::new();

        assert_eq!(
            EventTarget::User(7),
            process_event(
                &Event {
                    seq: 9,
                    user_id: 3,
                    action: Action::PrivateMessage(7, "hi".to_string()),
                },
                &mut followers,
                &mut blocked
            )
        );
    }

    #[test]
    fn process_block() {
        let mut followers = HashMap::new();
        let mut blocked = HashMap::new();

        assert_eq!(
            EventTarget::NoOne,
            process_event(
                &Event {
                    seq: 3,
                    user_id: 5,
                    action: Action::Block(7),
                },
                &mut followers,
                &mut blocked
            )
        );
        assert_eq!(Some(&hashset!{7}), blocked.get(&5));
    }

    #[test]
    fn process_block_second_user() {
        let mut followers = HashMap::new();
        let mut blocked = HashMap::new();
        blocked.insert(2, hashset!{5});

        assert_eq!(
            EventTarget::NoOne,
            process_event(
                &Event {
                    seq: 3,
                    user_id: 2,
                    action: Action::Block(7),
                },
                &mut followers,
                &mut blocked
            )
        );
        assert_eq!(Some(&hashset!{5, 7}), blocked.get(&2));
    }

}
