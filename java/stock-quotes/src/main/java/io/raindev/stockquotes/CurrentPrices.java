package io.raindev.stockquotes;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

class CurrentPrices {
    private final Map<String, InstrumentPrice> pricesByIsin = new ConcurrentHashMap<>();

    void handle(InstrumentMessage message) {
        pricesByIsin.computeIfAbsent(message.getData().getIsin(), isin ->
            new InstrumentPrice(isin, message.getData().getDescription(), Optional.empty()));
    }

    void handle(QuoteMessage message) {
        pricesByIsin.computeIfPresent(message.getData().getIsin(),
            (isin, oldPrice) -> new InstrumentPrice(
                isin, oldPrice.getDescription(), Optional.of(message.getData().getPrice())));
    }

    Collection<InstrumentPrice> list() {
        return pricesByIsin.values();
    }
}
