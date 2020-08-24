package io.raindev.stockquotes;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CurrentPricesTest {

    private final CurrentPrices currentPrices = new CurrentPrices();

    @Test
    void hasEmptyListOfInstrumentsInitially() {
        assertTrue(currentPrices.list().isEmpty());
    }

    @Test
    void newlyAddedInstrumentHasNoPrice() {
        currentPrices.handle(new InstrumentMessage(InstrumentMessage.Type.ADD,
            new InstrumentMessage.Data("description", "isin")));

        assertIterableEquals(
            Collections.singleton(new InstrumentPrice("isin", "description", Optional.empty())),
            currentPrices.list());
    }

    // TODO extend test suite

    @Test
    void recordsInstrumentPrice() {
    }

    @Test
    void retainsLatestPrice() {
    }

    @Test
    void removesInstrumentPrice() {
    }

    @Test
    void ignoresQuotesForMissingInstruments() {
    }

    @Test
    void ignoresInstrumentRemovalsForMissingInstruments() {
    }

    @Test
    void ignoresDuplicatedInstruments() {
    }

    @Test
    void recordsPricesForMultipleInstruments() {
        currentPrices.handle(new InstrumentMessage(InstrumentMessage.Type.ADD,
            new InstrumentMessage.Data("description1", "isin1")));
        currentPrices.handle(new InstrumentMessage(InstrumentMessage.Type.ADD,
            new InstrumentMessage.Data("description2", "isin2")));
        currentPrices.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin2", 92.93)));
        currentPrices.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin1", 5.67)));
        currentPrices.handle(new InstrumentMessage(InstrumentMessage.Type.ADD,
            new InstrumentMessage.Data("description3", "isin3")));
        currentPrices.handle(new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin3", 12.1937)));

        assertThat(currentPrices.list())
            .containsExactlyInAnyOrder(
                new InstrumentPrice("isin1", "description1", Optional.of(5.67)),
                new InstrumentPrice("isin2", "description2", Optional.of(92.93)),
                new InstrumentPrice("isin3", "description3", Optional.of(12.1937)));
    }

}
