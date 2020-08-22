package io.raindev.lights;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class LightsTest {

    static Stream<Arguments> testData() {
        return Stream.of(
                arguments(3, new int[] { 2, 1, 3, 5, 4}),
                arguments(2, new int[] { 2, 3, 4, 1, 5 }),
                arguments(3, new int[] { 1, 3, 4, 2, 5})
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void task(int moments, int[] lights) {
        assertEquals(moments, Lights.lightMoments(lights));
    }
}
