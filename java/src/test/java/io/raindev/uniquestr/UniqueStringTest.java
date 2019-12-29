package io.raindev.uniquestr;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static io.raindev.uniquestr.UniqueString.unique;

public class UniqueStringTest {

    @Test public void uniqueString() {
        assertTrue(unique("world"));
    }

    @Test public void nonUniqueString() {
        assertFalse(unique("Hello"));
    }

    @Test public void emptyString() {
        assertTrue(unique(""));
    }

}
