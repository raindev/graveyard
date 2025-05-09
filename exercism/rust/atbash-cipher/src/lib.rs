/// Enciphers a plan character or deciphers an enciphered one.
fn transform(c: char) -> char {
    if c.is_ascii_alphabetic() {
        (b'z' - (c as u8 - b'a')) as char
    } else {
        c
    }
}

/// "Encipher" with the Atbash cipher.
pub fn encode(plain: &str) -> String {
    let mut buf = String::new();
    let enciphered = plain
        .chars()
        .filter(char::is_ascii_alphanumeric)
        .map(|mut c| {
            c.make_ascii_lowercase();
            c
        })
        .map(transform);
    for (i, c) in enciphered.enumerate() {
        if i != 0 && i % 5 == 0 {
            buf.push(' ');
        }
        buf.push(c);
    }
    buf
}

/// "Decipher" with the Atbash cipher.
pub fn decode(cipher: &str) -> String {
    cipher
        .chars()
        .filter(|c| *c != ' ')
        .map(transform)
        .collect()
}
