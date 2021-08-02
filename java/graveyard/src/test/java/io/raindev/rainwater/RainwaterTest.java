package io.raindev.rainwater;

import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import static io.raindev.rainwater.Rainwater.trap;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RainwaterTest {

    @Test void largeTest() throws IOException {
        final int[] input = Files
            .lines(Paths.get("/tmp/input.txt"))
            .mapToInt(Integer::parseInt)
            .toArray();
        assertEquals(174801674, trap(input));
    }

    @Test void sample() {
        assertEquals(6, trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
    }
}
