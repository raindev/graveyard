pub fn median_sliding_window(nums: Vec<i32>, k: i32) -> Vec<f64> {
    // use a static storted buffer for the window, replace the passed number with the upcoming one
    // and ensure the buffer is still sorted each move
    nums.windows(k as usize)
        .map(|ns| {
            let mut sorted_nums = ns.to_vec();
            sorted_nums.sort();
            sorted_nums
        })
        .map(|ns| median(&ns))
        .collect()
}

fn median(slice: &[i32]) -> f64 {
    let len = slice.len();
    match slice.len() % 2 {
        1 => slice[len / 2] as f64,
        0 => (slice[len / 2 - 1] as f64 + slice[len / 2] as f64) / 2.0,
        _ => unreachable!(),
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn sample() {
        assert_eq!(
            vec![2.0f64, 3.0, 3.0, 3.0, 2.0, 3.0, 2.0],
            median_sliding_window(vec![1, 2, 3, 4, 2, 3, 1, 4, 2], 3)
        )
    }

    #[test]
    fn even() {
        assert_eq!(vec![2.5], median_sliding_window(vec![1, 4, 2, 3], 4))
    }
}
