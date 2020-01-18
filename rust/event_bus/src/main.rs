use event_bus::Result;

fn main() -> Result<()> {
    use event_bus::{
        parse_message, processor::process_events, source::source_events, user::start_user_acceptor,
        user::UserStreams,
    };
    use std::{
        io::BufReader,
        net::TcpListener,
        sync::{Arc, Mutex},
    };

    pretty_env_logger::init();
    let source_listener = TcpListener::bind("127.0.0.1:9999")?;
    log::info!(
        "Started source listener on {}",
        source_listener.local_addr()?
    );
    let user_listener = TcpListener::bind("127.0.0.1:9990")?;
    log::info!("Started user listener on {}", source_listener.local_addr()?);
    let (source_stream, _addr) = source_listener.accept()?;
    let mut source_reader = BufReader::new(source_stream);
    let total_users: usize = parse_message(&mut source_reader)?;
    log::info!("Total number of users: {}", total_users);
    let (sourced_events, source_handle) = source_events(source_reader);
    let user_streams = Arc::new(Mutex::new(UserStreams::new()));
    let user_handle = start_user_acceptor(user_listener, user_streams.clone());
    let processor_handle = process_events(sourced_events, user_streams.clone());
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
