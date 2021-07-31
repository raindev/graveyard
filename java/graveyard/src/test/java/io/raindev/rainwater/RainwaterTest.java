package io.raindev.rainwater;

import org.junit.jupiter.api.Test;

import static io.raindev.rainwater.Rainwater.trap;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RainwaterTest {
    @Test void test() {
        assertEquals(6, trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
    }
}
