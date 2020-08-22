package io.raindev.continuousints;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ContinuousIntsTest {
    @Test public void upToZero() {
        assertEquals(1, ContinuousInts.crack(new int[]{-1, 0}));
    }

    @Test public void consecutive() {
        assertEquals(4, ContinuousInts.crack(new int[]{1, 2, 3}));
    }

    @Test public void decreasing() {
        assertEquals(1, ContinuousInts.crack(new int[]{5, 4, 3, 2}));
    }

    @Test public void mix() {
        assertEquals(3, ContinuousInts.crack(new int[]{2, 2, 4, 1, 7}));
    }

}
