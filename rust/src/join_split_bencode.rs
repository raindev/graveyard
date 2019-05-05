pub fn join(ss: &Vec<String>) -> String {
    ss.iter().map(|s| format!("{}:{}", s.len(), s)).collect()
}

pub fn split(s: &str) -> Vec<String> {
    let mut length_index = 0;
    let mut result = Vec::new();
    loop {
        let next_colon = s[length_index..].find(":");
        if let Some(rel_colon_index) = next_colon {
            let colon_index = rel_colon_index + length_index;
            let length: usize = s[length_index..colon_index].parse().unwrap();
            length_index = colon_index + 1 + length;
            result.push(s[colon_index + 1..length_index].to_owned());
        } else {
            break;
        }
    }
    result
}

#[cfg(test)]
mod tests {
    extern crate quickcheck;

    use self::quickcheck::quickcheck;
    use super::*;

    fn to_str_vec(v: Vec<&str>) -> Vec<String> {
        v.iter().map(|s| str::to_owned(*s)).collect()
    }

    #[test]
    fn join_split() {
        let ss = to_str_vec(vec!["hi", "there", "people"]);
        println!("{}", join(&ss));
        assert_eq!(ss, split(&join(&ss)));
    }

    #[test]
    fn join_split_if_separator_present() {
        let ss = to_str_vec(vec!["h`i", "the``re", "people"]);
        assert_eq!(ss, split(&join(&ss)));
    }

    #[test]
    fn join_split_empty_string() {
        let ss = to_str_vec(vec![""]);
        assert_eq!(ss, split(&join(&ss)));
    }

    #[test]
    fn join_split_empty_vec() {
        let ss = to_str_vec(vec![]);
        assert_eq!(ss, split(&join(&ss)));
    }

    #[test]
    fn test_exhaustively() {
        fn prop(ss: Vec<String>) -> bool {
            ss == split(&join(&ss))
        }
        quickcheck(prop as fn(Vec<String>) -> bool);
    }
}
