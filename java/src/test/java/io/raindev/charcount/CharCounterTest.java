package io.raindev.charcount;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class CharCounterTest {
    @Test
    public void emptyStringNoCounters() {
        assertEquals(new HashMap<Character, Integer>(),
                CharCounter.countCharacters(""));
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void nullString() {
        assertThrows(NullPointerException.class, () -> CharCounter.countCharacters(null));
    }

    @Test
    public void nonRepeated() {
        HashMap<Character, Integer> expected = new HashMap<>();
        expected.put('a', 1);
        expected.put('b', 1);
        expected.put('c', 1);
        assertEquals(expected,
                CharCounter.countCharacters("abc"));
    }

    @Test
    public void repeated() {
        HashMap<Character, Integer> expected = new HashMap<>();
        expected.put('a', 2);
        expected.put('b', 1);
        expected.put('`', 3);
        assertEquals(expected,
                CharCounter.countCharacters("a`ab``"));
    }

}
