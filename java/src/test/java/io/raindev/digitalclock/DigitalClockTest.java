package io.raindev.digitalclock;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DigitalClockTest {

    public static String[] illegalDigits() {
        return new String[]{"", "5", "01234", "12:35:", "245959"};
    }

    @ParameterizedTest
    @MethodSource("illegalDigits")
    public void latestTimeIllegalArguments(String digits) {
        assertThrows(IllegalArgumentException.class,
                () -> DigitalClock.latestTime(digits));
    }

    @ParameterizedTest
    @CsvSource({
            "012345, 23:54:10",
            "211419, 21:49:11",
            "000000, 00:00:00",
            "995532, 23:59:59",
            "031120, 23:11:00",
            "900000, 09:00:00"
    })
    public void latestTime(String digits, String time) {
        assertEquals(time, DigitalClock.latestTime(digits));
    }

    @ParameterizedTest
    @CsvSource({
            "012345, 01:23:45",
            "211419, 11:12:49",
            "000000, 00:00:00",
            "995532, 23:59:59",
            "031120, 00:11:23",
            "999000, 09:09:09"
    })
    public void earliestTime(String digits, String time) {
        assertEquals(time, DigitalClock.earliestTime(digits));
    }

    @ParameterizedTest
    @MethodSource("illegalDigits")
    public void earliestTime(String digits) {
        assertThrows(IllegalArgumentException.class,
                () -> DigitalClock.earliestTime(digits));
    }
}
