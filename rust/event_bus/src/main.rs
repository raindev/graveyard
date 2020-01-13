use event_bus::Result;

fn main() -> Result<()> {
    use event_bus::{
        parse_message, source::source_events, user::start_user_acceptor, user::UserStreams,
    };
    use std::{
        io::BufReader,
        net::TcpListener,
        sync::{Arc, Mutex},
    };

    pretty_env_logger::init();
    let source_listener = TcpListener::bind("127.0.0.1:9999")?;
    log::debug!("Started source listener");
    let user_listener = TcpListener::bind("127.0.0.1:9990")?;
    log::debug!("Started user listener");
    let (source_stream, _addr) = source_listener.accept()?;
    let mut source_reader = BufReader::new(source_stream);
    let total_users: usize = parse_message(&mut source_reader)?;
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
