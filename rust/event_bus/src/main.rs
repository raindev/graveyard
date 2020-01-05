mod event;

use log;
use pretty_env_logger;
use std::{io::BufRead, io::BufReader, io::Read, net::TcpListener, thread, thread::JoinHandle};

type Error = Box<dyn std::error::Error>;
type Result<T> = std::result::Result<T, Error>;
type UserCount = usize;

/// Maximum number of events consumed from event source.
const EXPECTED_EVENT_COUNT: usize = 1_000_000;

#[test]
fn source_events_test() {
    assert_eq!(Some(5), total_users(&mut "5\n".as_bytes()).ok());
}

#[test]
fn source_events_empty() {
    assert!(total_users(&mut "".as_bytes()).is_err());
}

/// Reads total number of users from event source.
fn total_users<R: Read>(source: &mut R) -> Result<UserCount> {
    let mut source_reader = BufReader::new(source);
    let mut buff = String::new();
    source_reader.read_line(&mut buff)?;
    let user_count = buff.trim_end().parse::<UserCount>()?;
    Ok(user_count)
}

/// Processes stream of source events.
fn source_events<R: 'static + Read + Send>(events: R) -> JoinHandle<()> {
    thread::spawn(|| {
        let mut event_count = 0;
        for event_line in BufReader::new(events).lines() {
            let event_line = event_line.expect("failed to read event from source");
            log::trace!("Received event from source: {}", event_line);
            event_count += 1;
            if event_count == EXPECTED_EVENT_COUNT {
                log::info!("Processed all ({}) events", EXPECTED_EVENT_COUNT);
                break;
            }
        }
        if event_count != EXPECTED_EVENT_COUNT {
            log::warn!(
                "Expected {} events, only {} received",
                EXPECTED_EVENT_COUNT,
                event_count
            );
        }
    })
}

fn main() -> Result<()> {
    pretty_env_logger::init();
    let source_listener = TcpListener::bind("127.0.0.1:9999")?;
    let (mut source_stream, _addr) = source_listener.accept()?;
    log::info!(
        "Total number of users: {}",
        total_users(&mut source_stream)?
    );
    source_events(source_stream)
        .join()
        .expect("processing events from the source failed");
    Ok(())
}
