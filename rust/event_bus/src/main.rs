use event_bus::Result;

fn main() -> Result<()> {
    use event_bus::{
        processor::process_events, source::source_events, user::start_user_acceptor,
        user::UserStreams,
    };
    use std::{
        sync::{Arc, Mutex},
    };

    pretty_env_logger::init();

    let user_streams = Arc::new(Mutex::new(UserStreams::new()));
    let user_handle = start_user_acceptor(user_streams.clone());
    let (sourced_events, source_handle) = source_events();
    let processor_handle = process_events(sourced_events, user_streams);

    source_handle
        .join()
        .expect("processing events from the source failed");
    processor_handle
        .join()
        .expect("event processor failed");
    user_handle
        .join()
        .expect("user connection acceptor failed");
    Ok(())
}
