package io.raindev.stockquotes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

final class HotInstrumentDetector {

    public static final double VARIANCE_THRESHOLD = 0.1;

    private static final class PricePoint {
        private final Instant time;
        private final double price;

        private PricePoint(Instant time, double price) {
            this.time = time;
            this.price = price;
        }

    }

    private final Map<String, List<PricePoint>> recentPricesByIsin = new HashMap<>();
    private final Consumer<HotInstrumentMessage> eventConsumer;

    HotInstrumentDetector(Consumer<HotInstrumentMessage> eventConsumer) {
        this.eventConsumer = eventConsumer;
    }

    void handle(QuoteMessage quoteMessage) {
        // TODO remove price points older than 5 minutes

        final var isin = quoteMessage.getData().getIsin();
        var prices = recentPricesByIsin.computeIfAbsent(isin, k -> new ArrayList<>());

        final var newPrice = quoteMessage.getData().getPrice();
        if (prices.isEmpty()) {
            prices.add(new PricePoint(Instant.now(), newPrice));
            return;
        }

        final var minPrice = Collections.min(prices, Comparator.comparing(p -> p.price)).price;
        final var maxPrice = Collections.max(prices, Comparator.comparing(p -> p.price)).price;
        // TODO compute variance properly for price fall
        final var currentVariance = maxPrice / minPrice - 1;

        // TODO inject Clock to allow overriding in tests
        prices.add(new PricePoint(Instant.now(), newPrice));

        if (!(currentVariance > VARIANCE_THRESHOLD)) {
            if (newPrice < minPrice) {
                final var newVariance = maxPrice / newPrice - 1;
                if (newVariance > VARIANCE_THRESHOLD) {
                    eventConsumer.accept(new HotInstrumentMessage(isin, newVariance * 100,
                        newPrice));
                }
            } else if (newPrice > maxPrice) {
                final var newVariance = newPrice / minPrice - 1;
                if (newVariance > VARIANCE_THRESHOLD) {
                    eventConsumer.accept(new HotInstrumentMessage(isin, newVariance * 100,
                        newPrice));
                }
            }
        }

    }

    // TODO listen for instrument messages
}
