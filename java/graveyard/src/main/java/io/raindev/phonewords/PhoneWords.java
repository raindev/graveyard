package io.raindev.phonewords;

import java.util.List;
import java.util.ArrayList;

final class PhoneWords {
    private static final String[] letters = {
        "", "",
        "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
    };

    static List<String> combos(final String digits) {
        if (digits.isEmpty()) {
            return List.of();
        }
        List<String> res = new ArrayList<>();
        var digitCount = digits.length();
        var permutationCount = 1;
        for (int i = 0; i < digitCount; ++i) {
            permutationCount *= letters[digits.charAt(i) - '0'].length();
        }
        for (int i = 0; i < permutationCount; ++i) {
            var permutationNumber = i;
            var word = new StringBuilder();
            for (int digit = digitCount - 1; digit >= 0; --digit) {
                var digitLetters = letters[digits.charAt(digit) - '0'];
                var letterCount = digitLetters.length();
                word.insert(0, digitLetters.charAt(permutationNumber % letterCount));
                permutationNumber /= letterCount;
            }
            res.add(word.toString());
        }
        return res;
    }
}
