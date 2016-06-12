pub mod join_split {
    pub fn join(ss: &Vec<&str>) -> String {
        ss.iter()
            .map(escape)
            .collect::<Vec<String>>()
            .join("`$")
    }

    fn escape(s: &&str) -> String {
        s.replace('`', "``")
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
        if !next_str.is_empty() {
            res.push(next_str);
        }
        res
    }

    #[test]
    fn join_split() {
        let ss = vec!["hi", "there", "people"];
        assert_eq!(ss, split(&join(&ss)));
    }

    #[test]
    fn join_split_if_separator_present() {
        let ss = vec!["h`i", "the``re", "people"];
        assert_eq!(ss, split(&join(&ss)));
    }
}
