use std::{io::BufRead, io::Read, io::BufReader, net::TcpListener};

type Error = Box<dyn std::error::Error>;
type Result<T> = std::result::Result<T, Error>;
type UserCount = usize;

const EXPECTED_EVENT_COUNT: usize = 1_000_000;

#[test]
fn source_events_test() {
    assert_eq!(Some(5), source_events("5\n".as_bytes()).ok());
}

#[test]
fn source_events_empty() {
    assert!(source_events("".as_bytes()).is_err());
}

fn source_events<R: Read>(events: R) -> Result<UserCount> {
    let mut event_reader = BufReader::new(events);
    let mut buff = String::new();
    event_reader.read_line(&mut buff)?;
    let user_count = buff.trim_end().parse::<UserCount>()?;
    let mut event_count = 0;
    for event_line in event_reader.lines() {
        println!("Received event from source: {}", event_line?);
        event_count += 1;
        if event_count == EXPECTED_EVENT_COUNT {
            println!("Processed all ({}) events", EXPECTED_EVENT_COUNT);
            break;
        }
    }
    Result::Ok(user_count)
}

fn main() -> Result<()> {
    let source_listener = TcpListener::bind("127.0.0.1:9999")?;
    let (source_stream, _addr) = source_listener.accept()?;
    let user_count = source_events(BufReader::new(source_stream))?;
    println!("Total number of users: {}", user_count);
    Result::Ok(())
}
