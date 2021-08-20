package io.raindev.phonewords;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhoneWordsTest {
    @Test public void noDigits() {
        assertEquals(List.of(), PhoneWords.combos(""));
    }

    @Test public void oneDigit() {
        assertEquals(List.of("a","b","c"), PhoneWords.combos("2"));
    }

    @Test public void twoDigits() {
        assertEquals(
                List.of("ad","ae","af","bd","be","bf","cd","ce","cf"),
                PhoneWords.combos("23"));
    }
}
