package io.raindev.digitalclock;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DigitalClockTest {

    @ParameterizedTest
    @ValueSource(strings = {"", "5", "01234", "12:35:", "245959"})
    public void illegalArguments(String digits) {
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
    public void maxDigitalTime(String digits, String time) {
        assertEquals(time, DigitalClock.latestTime(digits));
    }
}
