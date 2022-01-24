pub fn min_unique_sum(numbers: &[u32]) -> u32 {
    use std::cmp::min;
    use std::collections::BTreeMap;

    if numbers.is_empty() {
        return 0;
    }

    let mut counts = BTreeMap::new();
    for number in numbers {
        *counts.entry(number).or_insert(0) += 1;
    }

    fn sum_between(n: u32, m: u32) -> u32 {
        if m < n {
            0
        } else {
            (m - n + 1) * (m + n) / 2
        }
    }

    let (first_number, first_count) = counts.iter().next().expect("early return for empty input");
    let mut overflow = first_count - 1;
    let mut prev_number = **first_number;
    let mut sum = prev_number;
    for (number, count) in counts.iter().skip(1) {
        sum += sum_between(prev_number + 1, min(prev_number + overflow, *number - 1));
        sum += *number;
        overflow = overflow.checked_sub(*number - prev_number - 1).unwrap_or(0) + count - 1;
        prev_number = **number;
    }
    if overflow != 0 {
        sum += (prev_number + 1..=(prev_number + overflow)).sum::<u32>();
    }

    sum
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn sample() {
        assert_eq!(17, min_unique_sum(&[3, 2, 1, 2, 7]));
    }

    #[test]
    fn empty() {
        assert_eq!(0, min_unique_sum(&[]));
    }

    #[test]
    fn starting_from_gap() {
        assert_eq!(5, min_unique_sum(&[5]));
    }

    #[test]
    fn non_filled_gap() {
        assert_eq!(14, min_unique_sum(&[0, 5, 9]));
    }

    #[test]
    fn duplicates() {
        assert_eq!(14, min_unique_sum(&[2, 2, 2, 2]));
    }

    #[test]
    fn overflow_within_gap() {
        assert_eq!(16, min_unique_sum(&[2, 2, 2, 7]));
    }

    #[test]
    fn overflow_gap() {
        assert_eq!(25, min_unique_sum(&[3, 3, 3, 3, 5]));
    }

    #[test]
    fn complete_sequence() {
        assert_eq!(6, min_unique_sum(&[0, 1, 2, 3]));
    }

    #[test]
    fn overflowing_sequence() {
        assert_eq!(10, min_unique_sum(&[0, 0, 1, 2, 3]));
    }
}

#[cfg(test)]
mod benchmark {
    extern crate rand;
    extern crate test;

    use self::test::Bencher;
    use super::*;

    #[bench]
    fn with_800_of_1000(b: &mut Bencher) {
        use self::rand::seq::SliceRandom;
        use self::rand::thread_rng;

        let mut rng = thread_rng();
        let mut input: Vec<u32> = (0..1000).collect();
        input.shuffle(&mut rng);
        for i in 0..100 {
            if input[i] < 100 {
                input[i] += 1
            }
        }
        b.iter(|| min_unique_sum(&input[0..800]));
    }
}
