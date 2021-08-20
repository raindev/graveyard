package io.raindev.phonewords;

import java.util.List;
import java.util.ArrayList;

final class PhoneWords {
    private static final String digitLetters(char digit) {
        switch (digit) {
            case '2': return "abc";
            case '3': return "def";
            case '4': return "ghi";
            case '5': return "jkl";
            case '6': return "mno";
            case '7': return "pqrs";
            case '8': return "tuv";
            case '9': return "wxyz";
            default: throw new IllegalArgumentException(
                             String.valueOf(digit));
        }
    }

    static List<String> combos(final String digits) {
        if (digits.isEmpty()) {
            return List.of();
        }
        List<String> res = new ArrayList<>();
        var digitCount = digits.length();
        String[] letters = new String[digitCount];
        var permutationCount = 1;
        for (int i = 0; i < digitCount; ++i) {
            letters[i] = digitLetters(digits.charAt(i));
            permutationCount *= letters[i].length();
        }
        for (int i = 0; i < permutationCount; ++i) {
            var permutationNumber = i;
            var word = new StringBuilder();
            for (int digit = digitCount - 1; digit >= 0; --digit) {
                var letterCount = letters[digit].length();
                word.insert(0, letters[digit].charAt(permutationNumber % letterCount));
                permutationNumber /= letterCount;
            }
            res.add(word.toString());
        }
        return res;
    }
}
