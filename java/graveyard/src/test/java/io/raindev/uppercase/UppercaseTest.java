package io.raindev.uppercase;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UppercaseTest {

    @Test
    public void singleWord() {
        assertEquals("HEY", Uppercase.of3Letters("hey"));
    }

    @Test
    public void twoWords() {
        assertEquals("HEY HEY", Uppercase.of3Letters("hey hey"));
    }

    @Test
    public void differentWordLength() {
        assertEquals("oh HEY hello", Uppercase.of3Letters("oh hey hello"));
    }

    @Test
    public void ignoreNonLetters() {
        assertEquals("oh! !HEY!", Uppercase.of3Letters("oh! !hey!"));
    }

    @Test
    public void trailingSpace() {
        assertEquals("HEY ", Uppercase.of3Letters("hey "));
    }

    @Test
    public void alreadyUppercase() {
        assertEquals("OH HEY HELLO", Uppercase.of3Letters("OH HEY HELLO"));
    }

    @Test
    public void empty() {
        assertEquals("", Uppercase.of3Letters(""));
    }

}
