package io.raindev.stockquotes;

import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriceHistoryTest {

    @Test
    void emptyPriceHistory() {
        var priceHistory = new PriceHistory(Clock::systemUTC);
        priceHistory.handle(new InstrumentMessage(InstrumentMessage.Type.ADD,
            new InstrumentMessage.Data("description", "isin")));

        assertTrue(priceHistory.forInstrument("isin").isEmpty());
    }

    @Test
    void extrapolateOneCandle() {
        var clock = new AtomicReference<>(Clock.fixed(Instant.parse("2020-05-03T10:17:33.00Z"),
            ZoneOffset.UTC));
        PriceHistory priceHistory = new PriceHistory(clock::get);

        priceHistory.handle(new InstrumentMessage(InstrumentMessage.Type.ADD,
            new InstrumentMessage.Data("description", "isin")));
        priceHistory.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 17.7)));
        priceHistory.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 17.6)));
        priceHistory.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 17.9)));
        priceHistory.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 17.8)));

        clock.set(Clock.offset(clock.get(), Duration.ofMinutes(15)));

        assertThat(priceHistory.forInstrument("isin"))
            .hasSize(15 + 1)
            .contains(new Candlestick(
                    Instant.parse("2020-05-03T10:17:00.00Z"),
                    Instant.parse("2020-05-03T10:18:00.00Z"),
                    17.7, 17.8, 17.6, 17.9),
                new Candlestick(
                    Instant.parse("2020-05-03T10:20:00.00Z"),
                    Instant.parse("2020-05-03T10:21:00.00Z"),
                    17.7, 17.8, 17.6, 17.9),
                new Candlestick(
                    Instant.parse("2020-05-03T10:30:00.00Z"),
                    Instant.parse("2020-05-03T10:31:00.00Z"),
                    17.7, 17.8, 17.6, 17.9));
    }

    @Test
    void extrapolateGapBetweenCandles() {
        var clock = new AtomicReference<>(Clock.fixed(Instant.parse("2020-05-03T10:17:33.00Z"),
            ZoneOffset.UTC));
        PriceHistory priceHistory = new PriceHistory(clock::get);

        priceHistory.handle(new InstrumentMessage(InstrumentMessage.Type.ADD,
            new InstrumentMessage.Data("description", "isin")));
        priceHistory.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 99)));
        clock.set(Clock.offset(clock.get(), Duration.ofMinutes(5)));
        priceHistory.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 100)));
        clock.set(Clock.offset(clock.get(), Duration.ofMinutes(3)));

        assertThat(priceHistory.forInstrument("isin"))
            .hasSize(5 + 3 + 1)
            .contains(new Candlestick(
                    Instant.parse("2020-05-03T10:17:00.00Z"),
                    Instant.parse("2020-05-03T10:18:00.00Z"),
                    99, 99, 99, 99),
                new Candlestick(
                    Instant.parse("2020-05-03T10:19:00.00Z"),
                    Instant.parse("2020-05-03T10:20:00.00Z"),
                    99, 99, 99, 99),
                new Candlestick(
                    Instant.parse("2020-05-03T10:22:00.00Z"),
                    Instant.parse("2020-05-03T10:23:00.00Z"),
                    100, 100, 100, 100),
                new Candlestick(
                    Instant.parse("2020-05-03T10:23:00.00Z"),
                    Instant.parse("2020-05-03T10:24:00.00Z"),
                    100, 100, 100, 100),
                new Candlestick(
                    Instant.parse("2020-05-03T10:25:00.00Z"),
                    Instant.parse("2020-05-03T10:26:00.00Z"),
                    100, 100, 100, 100));
    }

    @Test
    void stripOldHistory() {
        var clock = new AtomicReference<>(Clock.fixed(Instant.parse("2020-05-03T10:47:33.00Z"),
            ZoneOffset.UTC));
        PriceHistory priceHistory = new PriceHistory(clock::get);

        priceHistory.handle(new InstrumentMessage(InstrumentMessage.Type.ADD,
            new InstrumentMessage.Data("description", "isin")));
        priceHistory.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 10)));
        clock.set(Clock.offset(clock.get(), Duration.ofMinutes(60)));

        assertThat(priceHistory.forInstrument("isin"))
            .hasSize(31)
            .startsWith(new Candlestick(
                Instant.parse("2020-05-03T11:17:00.00Z"),
                Instant.parse("2020-05-03T11:18:00.00Z"),
                10, 10, 10, 10));
    }


    // TODO extend the test suite

    @Test
    void thrownExceptionIfNoInstrument() {
    }

    @Test
    void removeHistoryForInstrument() {
    }

}
