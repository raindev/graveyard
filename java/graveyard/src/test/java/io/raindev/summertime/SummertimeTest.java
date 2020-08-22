package io.raindev.summertime;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static io.raindev.summertime.Summertime.summerStart;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SummertimeTest {

    @Test public void noData() {
	assertEquals(Optional.empty(), summerStart(emptyList()));
    }

    @Test public void oneDaySummer() {
	assertEquals(Optional.of(0), summerStart(singletonList(26.2)));
    }

    @Test public void summerEverHotter() {
	assertEquals(Optional.of(0), summerStart(asList(14., 17., 20.)));
    }

    @Test public void summerWithADropAboveThreshold() {
	assertEquals(Optional.of(0), summerStart(asList(15., 18., 16., 19.)));
    }

    @Test public void temperatureDropBelowThreshold() {
	assertEquals(Optional.of(2), summerStart(asList(13., 12., 14., 21.)));
    }

    @Test public void multipleBelowThresholdTemperatureDrops() {
	assertEquals(Optional.of(4), summerStart(asList(9., 5., 13., 8., 14., 21.)));
    }

    @Test public void oneDropBelowThresholdAndOneAbove() {
	assertEquals(Optional.of(2), summerStart(asList(9., 5., 13., 10., 14., 21.)));
    }

    @Test public void summerStartsFromThreshold() {
	assertEquals(Optional.of(3), summerStart(asList(3., 5., 2., 5., 9.)));
    }

    @Test public void noSummer() {
	assertEquals(Optional.empty(), summerStart(asList(18., 17., 15.)));
    }

    @Test public void noSummerWithARiseBelowThreshold() {
	assertEquals(Optional.empty(), summerStart(asList(18., 12., 15., 13.)));
    }

}
