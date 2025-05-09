pub fn nth(n: usize) -> u32 {
    (2..)
        .filter(is_prime)
        .nth(n)
        .expect("n-th prime should always be computed")
}

fn is_prime(n: &u32) -> bool {
    (2..int_sqrt(*n))
        .all(|i| n % i != 0)
}

fn int_sqrt(n: u32) -> u32 {
    (n as f32).sqrt() as u32 + 1
}
