use std::str::FromStr;

type Error = Box<dyn std::error::Error>;
type Result<T> = std::result::Result<T, Error>;

type SeqNo = u64;
type UserId = u32;
type Message = String;

/// Represent an event related to user action.
#[derive(Debug, PartialEq)]
pub struct Event {
    /// Event sequence number.
    /// Represents its position in the global order of events.
    seq: SeqNo,
    /// ID of the user that triggered the event.
    user_id: UserId,
    /// Action performed by the user.
    action: Action,
}

/// Represents an action taken by a user.
#[derive(Debug, PartialEq)]
enum Action {
    Follow(UserId),
    Unfollow(UserId),
    StatusUpdate(Message),
    PrivateMessage(UserId, Message),
    Block(UserId),
}

impl FromStr for Event {
    type Err = Error;

    fn from_str(value: &str) -> Result<Self> {
        let mut event_fields = value.split('/');
        let seq = parse_next(&mut event_fields, "event sequence number")?;
        let action_tag = parse_next(&mut event_fields, "action tag")?;
        let user_id = parse_next(&mut event_fields, "actor user ID")?;
        let action = match action_tag {
            'F' => Action::Follow(parse_next(&mut event_fields, "target user ID")?),
            'U' => Action::Unfollow(parse_next(&mut event_fields, "target user ID")?),
            'S' => Action::StatusUpdate(next_non_empty_message(&mut event_fields)?),
            'P' => Action::PrivateMessage(
                parse_next(&mut event_fields, "target user ID")?,
                next_non_empty_message(&mut event_fields)?,
            ),
            'B' => Action::Block(parse_next(&mut event_fields, "target user ID")?),
            t => return Err(Error::from(format!("unexpected action tag: {}", t))),
        };
        Ok(Event {
            seq,
            user_id,
            action,
        })
    }
}

/// Parses a value from the next string produced by the iterator.
fn parse_next<'a, I, T>(iter: &mut I, err_description: &'static str) -> Result<T>
where
    I: Iterator<Item = &'a str>,
    T: FromStr,
    T::Err: std::error::Error + 'static,
{
    Ok(iter
        .next()
        .ok_or_else(|| format!("{} expected", err_description))?
        .parse()?)
}

/// Reads a string from the iterator. Returns an error if the string is empty.
fn next_non_empty_message<'a, I>(iter: &mut I) -> Result<String>
where
    I: Iterator<Item = &'a str>,
{
    let text = iter.next().ok_or("message text expected")?;
    if text.is_empty() {
        return Err(Error::from("message text is empty"));
    }
    Ok(text.to_string())
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn parse_follow_event() {
        assert_eq!(
            Some(Event {
                seq: 1,
                user_id: 5,
                action: Action::Follow(10),
            }),
            "1/F/5/10".parse().ok()
        );
    }

    #[test]
    fn parse_unfollow_event() {
        assert_eq!(
            Some(Event {
                seq: 78,
                user_id: 8,
                action: Action::Unfollow(16),
            }),
            "78/U/8/16".parse().ok()
        );
    }

    #[test]
    fn parse_status_update_event() {
        assert_eq!(
            Some(Event {
                seq: 8,
                user_id: 2,
                action: Action::StatusUpdate("Hello World".to_string()),
            }),
            "8/S/2/Hello World".parse().ok()
        );
    }

    #[test]
    fn parse_private_message_event() {
        assert_eq!(
            Some(Event {
                seq: 100,
                user_id: 3,
                action: Action::PrivateMessage(6, "Foo Bar".to_string()),
            }),
            "100/P/3/6/Foo Bar".parse().ok()
        );
    }

    #[test]
    fn parse_block_event() {
        assert_eq!(
            Some(Event {
                seq: 6,
                user_id: 200,
                action: Action::Block(43),
            }),
            "6/B/200/43".parse().ok()
        );
    }

    #[test]
    fn parse_empty_event() {
        assert!(Event::from_str("").is_err());
    }

    #[test]
    fn parse_event_non_numeric_seq() {
        assert!(Event::from_str("A/U/200/43").is_err());
    }

    #[test]
    fn parse_follow_event_no_target_user() {
        assert!(Event::from_str("100/F/3").is_err());
    }

    #[test]
    fn parse_private_message_event_empty_text() {
        assert!(Event::from_str("100/P/3/6/").is_err());
    }
}
