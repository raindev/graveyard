pub fn difference(n: u32) -> u32 {
    square_of_sum(n) - sum_of_squares(n)
}

pub fn square_of_sum(n: u32) -> u32 {
    n * n * (n + 1) * (n + 1) / 4
}

pub fn sum_of_squares(n: u32) -> u32 {
    n * (n + 1) * (2 * n + 1) / 6
}
