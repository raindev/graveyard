import java.util.HashSet;

final class IsogramChecker {

    boolean isIsogram(final String phrase) {
        return phrase.chars()
                .filter(Character::isAlphabetic)
                .map(Character::toLowerCase)
                // is an isogram if each character added to the set didn't occur before
                .allMatch(new HashSet<>()::add);
    }

}
