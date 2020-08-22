package io.raindev.digitalclock;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DigitalClock {
    /**
     * Finds latest 24 hour digital time that given digits can represent.
     *
     * @param digits a string of six digits
     * @return latest time {@code digits} can represent in format hh:mm::ss
     * @throws IllegalArgumentException if valid time cannot be constructed
     */
    public static String latestTime(String digits) {
        final List<Integer> digitsList = validDigits(digits);
        final var tensOfHours = removeLargest(digitsList, 2);
        return "" + tensOfHours + removeLargest(digitsList, tensOfHours == 2 ? 3 : 9)
                + ":" + removeLargest(digitsList, 5) + removeLargest(digitsList, 9)
                + ":" + removeLargest(digitsList, 5) + removeLargest(digitsList, 9);
    }

    /**
     * Find earliest 24 hour digital time that given digits can represent.
     *
     * @param digits a string of six digits
     * @return earliest time {@code digits} can represent in format hh:mm::ss
     * @throws IllegalArgumentException if valid time cannot be constructed
     */
    public static String earliestTime(String digits) {
        final List<Integer> digitsList = validDigits(digits);
        final var seconds = removeLargest(digitsList, 9);
        final var tensOfSeconds = removeLargest(digitsList, 5);
        final var minutes = removeLargest(digitsList, 9);
        final var tensOfMinutes = removeLargest(digitsList, 5);
        final var hours = removeLargest(digitsList, 9);
        final var tensOfHours = removeLargest(digitsList, hours > 3 ? 1 : 2);
        return "" + tensOfHours + hours + ":" + tensOfMinutes + minutes
                + ":" + tensOfSeconds + seconds;
    }

    public static List<Integer> validDigits(String digits) {
        if (digits.length() != 6 || !digits.chars().allMatch(Character::isDigit)) {
            throw new IllegalArgumentException();
        }
        return digits.chars()
                .mapToObj(Character::getNumericValue)
                .collect(Collectors.toList());
    }

    private static int removeLargest(List<Integer> numbers, int limit) {
        Optional<Integer> maxIndex = Optional.empty();
        for (int i = 0; i < numbers.size(); i++) {
            final var n = numbers.get(i);
            if (n == limit) {
                return numbers.remove(i);
            }
            if (n <= limit && maxIndex.map(mIdx -> numbers.get(mIdx) < n).orElse(true)) {
                maxIndex = Optional.of(i);
            }
        }
        return maxIndex.map(mIdx -> numbers.remove((int) mIdx))
                .orElseThrow(IllegalArgumentException::new);
    }
}
