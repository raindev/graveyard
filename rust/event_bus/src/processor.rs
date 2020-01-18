use crate::{event::Event, UserId};
use std::{
    collections::HashMap,
    io::Write,
    sync::{mpsc::Receiver, Arc, Mutex},
    thread::JoinHandle,
};

pub fn process_events<S>(
    events: Receiver<Event>,
    user_streams: Arc<Mutex<HashMap<UserId, S>>>,
) -> JoinHandle<()>
where
    S: Write + Send + 'static,
{
    use std::thread;

    let handle = thread::spawn(move || {
        log::debug!("Event processor started");
        for event in events {
            log::trace!("Processing {:?}", event);
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
    });
    handle
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::Result;

    #[test]
    fn forward_event() -> Result<()> {
        use crate::event::{Action, Event};
        use std::{sync::mpsc, io::BufWriter};

        let (sender, receiver) = mpsc::channel();
        let user_streams = Arc::new(Mutex::new(HashMap::<UserId, BufWriter<Vec<u8>>>::new()));
        user_streams.lock().unwrap().insert(42, BufWriter::new(Vec::new()));
        let _handle = process_events(receiver, user_streams.clone());
        sender.send(Event {
            seq: 5,
            user_id: 42,
            action: Action::StatusUpdate("hello".to_string()),
        })?;
        // TODO: Replace with a timed retry.
        std::thread::sleep(std::time::Duration::from_secs(1));
        let user_stream = user_streams.lock().unwrap();
        let message = String::from_utf8(user_stream.get(&42).unwrap().get_ref().clone())?;
        assert_eq!("5/S/42/hello\n", message);
        Ok(())
    }
}
