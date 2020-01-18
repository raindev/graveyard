use crate::event::Event;
use std::{
    io::Read,
    sync::{mpsc, mpsc::Receiver},
    thread::JoinHandle,
};

/// Maximum number of events consumed from event source.
const EXPECTED_EVENT_COUNT: usize = 1_000_000;

/// Processes stream of source events.
pub fn source_events<R: 'static + Read + Send>(events: R) -> (Receiver<Event>, JoinHandle<()>) {
    use std::{io::BufRead, io::BufReader, str::FromStr, thread};

    let (sender, receiver) = mpsc::channel();
    let handle = thread::spawn(move || {
        log::debug!("Source acceptor started");
        let mut event_count = 0;
        for event_line in BufReader::new(events).lines() {
            let event_line = event_line.expect("failed to read event from source");
            let event = Event::from_str(&event_line).expect("failed to parse event");
            log::trace!("Received event from source: {:?}", event);
            sender.send(event).expect("failed to send sourced event");
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
    });
    (receiver, handle)
}

#[test]
fn route_events() {
    let (receiver, _join_handle) = source_events("6/F/2/3\n7/U/2/3".as_bytes());
    assert!(receiver.recv().is_ok());
    assert!(receiver.recv().is_ok());
}
