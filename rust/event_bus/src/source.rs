use std::{io::Read, thread::JoinHandle};

/// Maximum number of events consumed from event source.
const EXPECTED_EVENT_COUNT: usize = 1_000_000;

/// Processes stream of source events.
pub fn source_events<R: 'static + Read + Send>(events: R) -> JoinHandle<()> {
    use std::{thread, io::BufReader, io::BufRead, str::FromStr};
    use crate::event::Event;

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
