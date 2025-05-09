#[derive(Debug, PartialEq)]
pub enum Comparison {
    Equal,
    Sublist,
    Superlist,
    Unequal,
}

pub fn sublist<T: PartialEq>(first: &[T], second: &[T]) -> Comparison {
    use Comparison::*;

    let is_sublist = |sublist: &[T], superlist: &[T]| {
        superlist
            .windows(sublist.len())
            .any(|window| window == sublist)
    };

    match (first, second) {
        (l, r) if l == r => Equal,
        (l, r) if l.is_empty() || is_sublist(l, r) => Sublist,
        (l, r) if r.is_empty() || is_sublist(r, l) => Superlist,
        _ => Unequal,
    }
}

