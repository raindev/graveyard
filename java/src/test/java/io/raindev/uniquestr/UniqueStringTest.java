package io.raindev.uniqueuestr;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
