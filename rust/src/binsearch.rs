pub fn find<T: PartialOrd + Copy>(slice: &[T], el: T) -> Option<usize> {
    if slice.is_empty() {
        return None;
    }
    let middle = slice.len() / 2;
    let middle_el = slice[middle];
    if middle_el == el {
        return Some(middle);
    }
    let higher = el > middle_el;
    if higher {
        find(&slice[middle + 1..], el).map(|x| x + middle + 1)
    } else {
        find(&slice[0..middle], el)
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn find_in_empty() {
        assert_eq!(None, find(&[], 2));
    }

    #[test]
    fn find_existing() {
        assert_eq!(Some(1), find(&[3, 4, 5, 9], 4));
    }

    #[test]
    fn find_existing_tail() {
        assert_eq!(Some(3), find(&[3, 4, 5, 9], 9));
    }

    #[test]
    fn find_missing_in_the_middle() {
        assert_eq!(None, find(&[3, 4, 5], 6));
    }

    #[test]
    fn find_missing_head() {
        assert_eq!(None, find(&[3, 4], 1));
    }

    #[test]
    fn find_missing_tail() {
        assert_eq!(None, find(&[3, 4], 5));
    }

    #[test]
    fn find_in_singleton() {
        assert_eq!(Some(0), find(&[2], 2));
    }

    #[test]
    fn find_missing_in_singleton() {
        assert_eq!(None, find(&[2], 1));
    }
}
