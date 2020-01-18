use crate::UserId;
use bufstream::BufStream;
use std::{
    collections::HashMap,
    net::{TcpListener, TcpStream},
    sync::{Arc, Mutex},
    thread::JoinHandle,
};

pub type UserStreams = HashMap<UserId, BufStream<TcpStream>>;

pub fn start_user_acceptor(
    tcp_listener: TcpListener,
    user_streams: Arc<Mutex<UserStreams>>,
) -> JoinHandle<()> {
    use crate::parse_message;
    use std::thread;

    thread::spawn(move || {
        log::debug!("User acceptor started");
        for stream in tcp_listener.incoming() {
            let stream = stream.expect("user connection failed");
            let mut buf_reader = BufStream::new(stream);
            let user_id =
                parse_message(&mut buf_reader).expect("failed to parse ID of connecting user");
            log::debug!("User {} connected", user_id);
            user_streams
                .lock()
                .expect("failed to acquire user streams lock")
                .insert(user_id, buf_reader);
        }
    })
}

#[cfg(test)]
mod tests {
    use super::*;
    use crate::Result;

    #[test]
    fn accept_user_connection() -> Result<()> {
        use std::io::Write;

        pretty_env_logger::init();
        let streams = Arc::new(Mutex::new(UserStreams::new()));
        let listener = TcpListener::bind("localhost:0")?;
        let server_addr = listener.local_addr()?;
        let _handle = start_user_acceptor(listener, streams.clone());
        let mut user_stream = TcpStream::connect(server_addr)?;
        user_stream.write_fmt(format_args!("42\n"))?;

        // TODO: Limit retry time and not number.
        let mut retries = 100;
        while retries > 0 && streams.lock().unwrap().get(&42).is_none() {
            retries -= 1;
        }
        assert!(streams.lock().unwrap().get(&42).is_some());

        Ok(())
    }
}
