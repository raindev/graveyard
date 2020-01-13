use crate::UserId;
use std::{
    collections::HashMap,
    io::BufReader,
    net::{TcpListener, TcpStream},
    sync::Arc,
    sync::Mutex,
    thread::JoinHandle,
};

pub type UserStreams = HashMap<UserId, BufReader<TcpStream>>;

pub fn start_user_acceptor(
    tcp_listener: TcpListener,
    user_streams: Arc<Mutex<UserStreams>>,
) -> JoinHandle<()> {
    use std::thread;
    use crate::parse_message;

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

#[test]
fn accept_user_connection() {
    use std::io::Write;

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
