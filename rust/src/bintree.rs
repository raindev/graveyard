#[allow(dead_code)]
struct Tree {
    n: i32,
    left: Option<Box<Tree>>,
    right: Option<Box<Tree>>,
}

#[cfg(test)]
mod tests {
    use super::Tree;

    #[test]
    fn empty() {
        let _t = Tree {
            n: 0,
            left: None,
            right: None,
        };
    }

    #[test]
    fn create() {
        let t = Tree {
            n: 0,
            left: Some(Box::new(Tree {
                n: 1,
                left: None,
                right: None,
            })),
            right: Some(Box::new(Tree {
                n: 2,
                left: None,
                right: None,
            })),
        };
        assert_eq!(1, t.left.unwrap().n);
        assert_eq!(2, t.right.unwrap().n);
    }

    #[test]
    fn modify() {
        let mut t = Tree {
            n: 2,
            left: None,
            right: None,
        };
        t.n = 1;
        assert_eq!(1, t.n);
        t.right = Some(Box::new(Tree {
            n: -1,
            left: None,
            right: None,
        }));
        assert_eq!(-1, t.right.unwrap().n);
    }
}
