package io.raindev.binarystring;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class BinaryStringTest {

    static Stream<Arguments> testData() {
        return Stream.of(
                arguments(7, "000011100"),
                arguments(5, "1010"),
                arguments(1, "1"),
                arguments(0, "0"),
                arguments(0, "0000"),
                arguments(0, "")
        );
    }

    @ParameterizedTest
    @MethodSource("testData")
    void task(int steps, String binaryNumber) {
        assertEquals(steps, BinaryString.stepsToZero(binaryNumber));
    }

}
