mod event;

use crate::event::Event;
use event_bus::{parse_message, Result, UserCount, UserId};
use log;
use pretty_env_logger;
use std::{
    collections::HashMap,
    io::BufRead,
    io::BufReader,
    io::Read,
    net::{TcpListener, TcpStream},
    str::FromStr,
    thread,
    thread::JoinHandle,
};

/// Maximum number of events consumed from event source.
const EXPECTED_EVENT_COUNT: usize = 1_000_000;

/// Processes stream of source events.
fn source_events<R: 'static + Read + Send>(events: R) -> JoinHandle<()> {
    thread::spawn(|| {
        let mut event_count = 0;
        for event_line in BufReader::new(events).lines() {
            let event_line = event_line.expect("failed to read event from source");
            log::trace!(
                "Received event from source: {:?}",
                Event::from_str(&event_line).expect("failed to parse event")
            );
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

fn start_user_acceptor(
    tcp_listener: TcpListener,
    mut user_streams: HashMap<UserId, BufReader<TcpStream>>,
) -> JoinHandle<()> {
    thread::spawn(move || {
        for stream in tcp_listener.incoming() {
            let stream = stream.expect("user connection failed");
            let mut reader = BufReader::new(stream);
            let user_id =
                parse_message(&mut reader).expect("failed to parse ID of connecting user");
            log::debug!("User {} connected", user_id);
            user_streams.insert(user_id, reader);
        }
    })
}

fn main() -> Result<()> {
    pretty_env_logger::init();
    let source_listener = TcpListener::bind("127.0.0.1:9999")?;
    log::debug!("Started source listener");
    let user_listener = TcpListener::bind("127.0.0.1:9990")?;
    log::debug!("Started user listener");
    let (source_stream, _addr) = source_listener.accept()?;
    let mut source_reader = BufReader::new(source_stream);
    let total_users: UserCount = parse_message(&mut source_reader)?;
    log::info!("Total number of users: {}", total_users);
    source_events(source_reader)
        .join()
        .expect("processing events from the source failed");
    let user_streams = HashMap::<UserId, BufReader<TcpStream>>::new();
    start_user_acceptor(user_listener, user_streams)
        .join()
        .expect("user connection acceptor failed");
    Ok(())
}
