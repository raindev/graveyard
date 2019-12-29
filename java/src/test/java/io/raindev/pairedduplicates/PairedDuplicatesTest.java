package io.raindev.pairedduplicates;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PairedDuplicatesTest {

    static Stream<Arguments> testData() {
        return Stream.of(
                arguments(4, new int[]{3, 5, 6, 3, 3, 5}),
                arguments(7, new int[]{3, 5, 3, 3, 3, 5}),
                arguments(0, new int[]{}),
                arguments(0, new int[]{1}),
                arguments(15, new int[]{1, 1, 1, 1, 1, 1}),
                arguments(0, new int[]{-2, -1, 0, 1, 2}),
                arguments(2, new int[]{-1, 2, -1, 0, 5, 5})
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void task(int pairsCount, int[] numbers) {
        assertEquals(pairsCount, PairedDuplicates.pairsCount(numbers));
    }
}
