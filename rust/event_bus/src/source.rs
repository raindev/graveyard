use crate::event::Event;
use std::{
    sync::{mpsc, mpsc::Receiver},
    thread::JoinHandle,
};

/// Maximum number of events consumed from event source.
const EXPECTED_EVENT_COUNT: usize = 1_000_000;

/// Processes stream of source events.
pub fn source_events() -> (Receiver<Event>, JoinHandle<()>) {
    use crate::parse_message;
    use std::{
        cmp::Reverse, collections::BinaryHeap, io::BufRead, io::BufReader, net::TcpListener,
        str::FromStr, thread,
    };

    let source_listener = TcpListener::bind("127.0.0.1:9999").expect("source listener failed");
    log::info!(
        "Started source listener on {}",
        source_listener
            .local_addr()
            .expect("failed to get source server address")
    );

    let (sender, receiver) = mpsc::channel();
    let handle = thread::spawn(move || {
        log::debug!("Source acceptor started");
        let (source_stream, _addr) = source_listener.accept().expect("source connection failed");
        let mut source_reader = BufReader::new(source_stream);
        let total_users: usize =
            parse_message(&mut source_reader).expect("failed to read user number");
        log::info!("Total number of users expected {}", total_users);

        let mut event_queue = BinaryHeap::new();
        let mut event_count = 0;
        let mut next_seq = 1;
        for event_line in source_reader.lines() {
            let event_line = event_line.expect("failed to read event from source");
            let event = Event::from_str(&event_line).expect("failed to parse event");

            log::trace!("Buffering event received from source {:?}", event);
            event_queue.push(Reverse(event));
            while event_queue
                .peek()
                .map(|Reverse(e)| e.seq == next_seq)
                .unwrap_or(false)
            {
                let Reverse(event) = event_queue.pop().unwrap();
                log::trace!("Forwarding sourced event {:?}", event);
                sender.send(event).expect("failed to send sourced event");
                next_seq += 1;
            }
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

#[cfg(test)]
mod tests {
    use super::*;
    use crate::Result;

    #[test]
    fn route_events() -> Result<()> {
        use std::{io::Write, net::TcpStream};


        let (receiver, _join_handle) = source_events();
        let mut source_stream = TcpStream::connect("127.0.0.1:9999")?;
        write!(
            &mut source_stream,
            "10\n2/U/2/3\n1/B/2/9\n3/F/2/3\n4/S/2/hi\n"
        )?;
        assert_eq!(1, receiver.recv().ok().unwrap().seq);
        assert_eq!(2, receiver.recv().ok().unwrap().seq);
        assert_eq!(3, receiver.recv().ok().unwrap().seq);
        assert_eq!(4, receiver.recv().ok().unwrap().seq);
        Ok(())
    }
}
