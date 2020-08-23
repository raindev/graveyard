package io.raindev.stockquotes;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Supplier;

final class PriceHistory {
    private static final Duration CANDLESTICK_INTERVAL = Duration.ofMinutes(1);
    private static final Duration HISTORY_INTERVAL = Duration.ofMinutes(30);
    private final Map<String, List<Candlestick>> historiesByIsin = new ConcurrentHashMap<>();
    private final Supplier<Clock> clockProvider;

    PriceHistory(Supplier<Clock> clockProvider) {
        this.clockProvider = clockProvider;
    }

    // TODO handle removals
    void handle(InstrumentMessage message) {
        historiesByIsin.computeIfAbsent(message.getData().getIsin(), isin ->
            // TODO This is a thread safe but naive from performance point of view approach.
            // Efficient candlestick updates/removals can be implemented with ConcurrentLinkedDeque.
            new CopyOnWriteArrayList<>());
    }

    void handle(QuoteMessage message) {
        final var currentOpenTime = currentOpenTime();
        final var newPrice = message.getData().getPrice();
        final var history = historiesByIsin.get(message.getData().getIsin());

        if (history == null) {
            // TODO report the error to the caller
            return;
        }

        if (history.isEmpty()
            || !history.get(history.size() - 1).getOpenTime().equals(currentOpenTime)) {
            history.add(newCandle(currentOpenTime, newPrice));
            return;
        }

        final var lastCandle = history.get(history.size() - 1);
        history.set(history.size() - 1,
            new Candlestick(
                lastCandle.getOpenTime(),
                lastCandle.getCloseTime(),
                lastCandle.getOpenPrice(),
                newPrice,
                Math.min(lastCandle.getLowPrice(), newPrice),
                Math.max(lastCandle.getHighPrice(), newPrice)));
    }

    private Instant currentOpenTime() {
        return Clock.tick(clockProvider.get(), CANDLESTICK_INTERVAL).instant();
    }

    private Candlestick newCandle(Instant currentOpenTime, double newPrice) {
        return new Candlestick(currentOpenTime,
            currentOpenTime.plus(CANDLESTICK_INTERVAL), newPrice, newPrice, newPrice, newPrice);
    }

    Collection<Candlestick> forInstrument(String isin) {
        final var history = historiesByIsin.get(isin);
        if (history == null || history.isEmpty()) {
            return Collections.emptyList();
        }

        // TODO trigger clean-up of old data points

        final var currentCloseTime = currentOpenTime().plus(CANDLESTICK_INTERVAL);
        // To cover the moment in time 30 minutes ago the history has to start 31 full minutes ago.
        final var startOpenTime = currentCloseTime.minus(HISTORY_INTERVAL)
            .minus(CANDLESTICK_INTERVAL);
        final List<Candlestick> result = new ArrayList<>();
        Candlestick lastCandlestick = null;
        for (final Candlestick candlestick : history) {
            if (lastCandlestick != null) {
                interpolate(result, lastCandlestick, startOpenTime, candlestick.getOpenTime());
            }
            if (candlestick.getOpenTime().isAfter(startOpenTime)) {
                result.add(candlestick);
            }
            lastCandlestick = candlestick;
        }
        interpolate(result, lastCandlestick, startOpenTime, currentCloseTime);
        return result;
    }

    private void interpolate(final List<Candlestick> result, Candlestick source,
                             Instant historyOpenTime, Instant closeTime) {
        var candlestick = source;
        while (!candlestick.getCloseTime().equals(closeTime)) {
            candlestick = new Candlestick(candlestick.getCloseTime(),
                candlestick.getCloseTime().plus(CANDLESTICK_INTERVAL),
                candlestick.getOpenPrice(), candlestick.getClosePrice(),
                candlestick.getLowPrice(), candlestick.getHighPrice());
            if (candlestick.getCloseTime().isAfter(historyOpenTime)) {
                result.add(candlestick);
            }
        }
    }

}
