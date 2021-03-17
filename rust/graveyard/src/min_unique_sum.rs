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
    fn test() {
        assert_eq!(17, min_unique_sum(&[3, 2, 1, 2, 7]));
    }
}
