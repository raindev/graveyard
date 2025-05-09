import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

final class Anagram {
    private final String word;
    private final Map<Character, Long> charFrequencies;

    Anagram(final String word) {
        this.word = word.toLowerCase();
        charFrequencies = charFrequencies(word);
    }

    Collection<String> match(final Collection<String> candidates) {
        return candidates.stream()
            .filter(candidate -> !candidate.toLowerCase().equals(word))
            .filter(candidate -> charFrequencies(candidate).equals(charFrequencies))
            .collect(Collectors.toList());
    }

    private Map<Character, Long> charFrequencies(final String word) {
        return word.chars()
            .mapToObj(c -> (char) c)
            .map(Character::toLowerCase)
            .collect(Collectors.groupingBy(
                        Function.identity(), Collectors.counting()));
    }

}
