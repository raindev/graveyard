pub fn min_unique_sum(numbers: &[u32]) -> u32 {
    use std::cmp::max;

    let input_range = 0..1000;
    assert!(numbers.len() <= input_range.len());

    let mut counts = [0; 1000];
    for n in numbers {
        assert!(input_range.contains(n));
        counts[*n as usize] += 1;
    }
    let mut sum = 0;
    let mut overflow = 0;
    for (n, count) in counts.iter().enumerate() {
        if *count != 0 || overflow != 0 {
            sum += n as u32;
        }
        if *count > 1 {
            overflow += count - 1;
        } else if *count == 0 {
            overflow = max(0, overflow - 1);
        }
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
    extern crate test;
    extern crate rand;

    use super::*;
    use self::test::Bencher;

    #[bench]
    fn with_800_of_1000(b: &mut Bencher) {
        use self::rand::thread_rng;
        use self::rand::seq::SliceRandom;

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
