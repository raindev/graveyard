use std::collections::HashSet;

pub fn anagrams_for<'a>(word: &str, possible_anagrams: &[&'a str]) -> HashSet<&'a str> {
    use std::collections::HashMap;

    fn char_counts(word: &str) -> HashMap<char, u64> {
        word.chars()
            .flat_map(char::to_lowercase)
            .fold(HashMap::new(), |mut counts, chr| {
                *counts.entry(chr).or_insert(0) += 1;
                counts
            })
    }

    fn eq_ignoring_case(str1: &str, str2: &str) -> bool {
        str1.chars()
            .flat_map(char::to_lowercase)
            .eq(str2.chars().flat_map(char::to_lowercase))
    }

    let word_chars_counts = char_counts(word);
    possible_anagrams
        .iter()
        .filter(|candidate| {
            candidate.len() == word.len()
                && !eq_ignoring_case(candidate, word)
                && char_counts(candidate) == word_chars_counts
        })
        // copy the original references as the iterator gives us &&str
        .copied()
        .collect()
}
