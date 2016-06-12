pub mod join_split {
    pub fn join(ss: &Vec<String>) -> String {
        if ss.is_empty() {
            return String::new()
        }
        let mut string = ss.iter()
            .map(|s| s.replace('`', "``"))
            .collect::<Vec<String>>()
            .join("`$");
        string.push_str("`$");
        string
    }

    pub fn split(s: &str) -> Vec<String> {
        let mut res = Vec::new();
        let mut next_str = String::new();
        let mut escaped = false;
        for chr in s.chars() {
            if escaped {
                if chr == '`' {
                    next_str.push('`');
                } else if chr == '$' {
                    res.push(next_str);
                    next_str = String::new();
                }
                escaped = false;
            } else if chr == '`' {
                escaped = true;
            } else {
                next_str.push(chr);
            }
        }
        res
    }

}

#[cfg(test)]
mod test_join_split {
    use join_split::*;
    extern crate quickcheck;
    use self::quickcheck::quickcheck;

    fn to_str_vec(v: Vec<&str>) -> Vec<String> {
        v.iter().map(|s| str::to_owned(*s)).collect()
    }

    #[test]
    fn join_split() {
        let ss = to_str_vec(vec!["hi", "there", "people"]);
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
