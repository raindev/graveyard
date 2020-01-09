mod event;

use crate::event::Event;
use event_bus::{parse_message, Result, UserCount, UserId};
use log;
use pretty_env_logger;
use std::{
    collections::HashMap,
    io::{BufRead, BufReader, Read, Write},
    net::{TcpListener, TcpStream},
    str::FromStr,
    sync::{Arc, Mutex},
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

#[test]
fn accept_user_connection() {
    pretty_env_logger::init();
    let streams = Arc::new(Mutex::new(UserStreams::new()));
    let listener = TcpListener::bind("localhost:0").expect("failed to bind listener");
    let server_addr = listener.local_addr().expect("failed to get server address");
    let _ = start_user_acceptor(listener, streams.clone());
    let mut user_stream = TcpStream::connect(server_addr).expect("connection failed");
    user_stream
        .write_fmt(format_args!("42\n"))
        .expect("failed to send user ID");

    let mut retries = 100;
    while retries > 0 && streams.lock().unwrap().get(&42).is_none() {
        retries -= 1;
    }
    assert!(streams.lock().unwrap().get(&42).is_some());
}

type UserStreams = HashMap<UserId, BufReader<TcpStream>>;

fn start_user_acceptor(
    tcp_listener: TcpListener,
    user_streams: Arc<Mutex<UserStreams>>,
) -> JoinHandle<()> {
    thread::spawn(move || {
        log::debug!("user acceptor started");
        for stream in tcp_listener.incoming() {
            let stream = stream.expect("user connection failed");
            let mut reader = BufReader::new(stream);
            let user_id =
                parse_message(&mut reader).expect("failed to parse ID of connecting user");
            log::debug!("User {} connected", user_id);
            user_streams
                .lock()
                .expect("failed to acquire user streams lock")
                .insert(user_id, reader);
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
    let user_streams = UserStreams::new();
    start_user_acceptor(user_listener, Arc::new(Mutex::new(user_streams)))
        .join()
        .expect("user connection acceptor failed");
    Ok(())
}
