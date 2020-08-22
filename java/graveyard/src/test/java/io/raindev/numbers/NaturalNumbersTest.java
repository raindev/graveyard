package io.raindev.numbers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class NaturalNumbersTest {

    static Stream<Arguments> testData() {
        return Stream.of(
                arguments(4, new int[]{3, 2, 1, 5}),
                arguments(4, new int[]{1, 2, 3}),
                arguments(1, new int[]{-1, -3}),
                arguments(5, new int[]{1, 3, 6, 4, 1, 2}),
                arguments(1, new int[]{}),
                arguments(2, new int[]{1}),
                arguments(1, new int[]{0})
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void task(int n, int[] numbers) {
        assertEquals(n, new NaturalNumbers().smallestMissing(numbers));
    }
}
