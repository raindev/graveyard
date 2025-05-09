/// Check a Luhn checksum.
pub fn is_valid(code: &str) -> bool {
    code.chars()
        .rev()
        .filter(|&chr| chr != ' ')
        .map(|chr| chr.to_digit(10))
        .enumerate()
        .map(|(i, digit)| {
            digit.map(|d| {
                if i % 2 == 1 {
                    if d * 2 > 9 {
                        d * 2 - 9
                    } else {
                        d * 2
                    }
                } else {
                    d
                }
            })
        })
        .try_fold((0, 0), |(count, sum), digit| {
            digit.map(|d| (count + 1, sum + d))
        })
        .map_or(false, |(count, sum)| count > 1 && sum % 10 == 0)
}
