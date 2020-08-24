package io.raindev.stockquotes;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class HotInstrumentDetectorTest {

    @Test
    void noEventOnInsignificantPriceChange() {
        var detector = new HotInstrumentDetector(event -> fail("No events are expected"));

        detector.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 20)));
        detector.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 20.5)));
    }

    @Test
    void emitsEventOnSignificantPriceChange() {
        var eventCount = new AtomicInteger(0);
        var detector = new HotInstrumentDetector(e -> eventCount.incrementAndGet());

        detector.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 20)));
        detector.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 20.5)));
        assertEquals(0, eventCount.get());

        detector.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 25.5)));

        // TODO verify event details
        assertEquals(1, eventCount.get());
    }

    @Test
    void noSecondEvenOnPriceChange() {
        var eventCount = new AtomicInteger(0);
        var detector = new HotInstrumentDetector(e -> eventCount.incrementAndGet());

        detector.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 20)));
        detector.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 25)));
        assertEquals(1, eventCount.get());

        detector.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 25.5)));

        assertEquals(1, eventCount.get());
    }

    // TODO extend test suite

    @Test
    void emitsEventAfterNewPriceChange() {
    }

    @Test
    void noEventOnDistantPriceChange() {
    }

}
