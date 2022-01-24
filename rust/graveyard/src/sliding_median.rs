pub fn median_sliding_window(nums: &[i32], k: usize) -> Vec<f64> {
    if nums.len() < k {
        return vec![];
    }

    // use a static sorted buffer for the window, replace the passed number with the upcoming one
    // and ensure the buffer is still sorted each move
    let mut buff = nums[0..k].to_vec();
    buff.sort_unstable();
    let mut res = vec![];
    for i in k..nums.len() {
        res.push(median(&buff));
        replace(&mut buff, nums[i - k], nums[i]);
    }
    res.push(median(&buff));
    res
}

fn replace(buff: &mut Vec<i32>, old: i32, new: i32) {
    let old_idx = buff.binary_search(&old).unwrap();
    buff.remove(old_idx);
    let new_idx = buff.binary_search(&new).unwrap_or_else(|x| x);
    buff.insert(new_idx, new);
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
            median_sliding_window(&vec![1, 2, 3, 4, 2, 3, 1, 4, 2], 3)
        )
    }

    #[test]
    fn even_items() {
        assert_eq!(vec![2.5], median_sliding_window(&vec![1, 4, 2, 3], 4))
    }

    #[test]
    fn odd_items() {
        assert_eq!(vec![3.0], median_sliding_window(&vec![1, 4, 6, 2, 3], 5))
    }

    #[test]
    fn i32_overflow() {
        assert_eq!(
            vec![i32::MAX as f64],
            median_sliding_window(&vec![i32::MAX, i32::MAX], 2)
        )
    }

    #[test]
    fn oversized_window() {
        assert_eq!(Vec::<f64>::new(), median_sliding_window(&vec![2, 3], 3))
    }
}

#[cfg(test)]
mod benchmark {
    extern crate rand;
    extern crate test;

    use self::test::Bencher;
    use super::*;

    #[bench]
    fn with_100_and_10_00(b: &mut Bencher) {
        use self::rand::seq::SliceRandom;
        use self::rand::thread_rng;

        let mut rng = thread_rng();
        let mut input: Vec<i32> = (0..10_000).collect();
        input.shuffle(&mut rng);
        b.iter(|| median_sliding_window(&input, 100));
    }
}
