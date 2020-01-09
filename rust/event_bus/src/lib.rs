use std::{
    io::{BufRead},
    str::FromStr,
};

pub type Error = Box<dyn std::error::Error>;
pub type Result<T> = std::result::Result<T, Error>;
pub type UserCount = usize;
pub type UserId = u32;

/// Reads total number of users from event source.
pub fn parse_message<R, T>(source: &mut R) -> Result<T>
where
    R: BufRead,
    T: FromStr,
    T::Err: std::error::Error + 'static,
{
    let mut buff = String::new();
    source.read_line(&mut buff)?;
    Ok(buff.trim_end().parse()?)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn parse_number_message() {
        let result: Result<u32> = parse_message(&mut "51\n".as_bytes());
        assert_eq!(Some(51), result.ok());
    }

    #[test]
    fn parse_string() {
        let result: Result<String> = parse_message(&mut "hello\n".as_bytes());
        assert_eq!(Some("hello".to_string()), result.ok());
    }

    #[test]
    fn parse_first_message() {
        let result: Result<u32> =
            parse_message(&mut "12\n1/F/5/10\n8/S/2/status-update\n".as_bytes());
        assert_eq!(Some(12), result.ok());
    }

    #[test]
    fn parse_multiple_messages() {
        let mut stream = "first\nsecond\n".as_bytes();
        let first_result: Result<String> = parse_message(&mut stream);
        assert_eq!(Some("first".to_string()), first_result.ok());
        let second_result: Result<String> = parse_message(&mut stream);
        assert_eq!(Some("second".to_string()), second_result.ok());
    }

    #[test]
    fn parse_empty_stream() {
        let result: Result<u32> = parse_message(&mut "".as_bytes());
        assert!(result.is_err());
    }

    #[test]
    fn parsing_error() {
        let result: Result<u32> = parse_message(&mut "a".as_bytes());
        assert!(result.is_err());
    }
}
