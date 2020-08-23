package io.raindev.stockquotes;

import java.time.Instant;
import java.util.Objects;
import java.util.StringJoiner;

class Candlestick {
    private final Instant openTime;
    private final Instant closeTime;

    private final double openPrice;
    private final double closePrice;
    private final double lowPrice;
    private final double highPrice;

    Candlestick(Instant openTime, Instant closeTime, double openPrice, double closePrice,
                double lowPrice, double highPrice) {
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
    }

    public Instant getOpenTime() {
        return openTime;
    }

    public Instant getCloseTime() {
        return closeTime;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candlestick candlestick = (Candlestick) o;
        return Double.compare(candlestick.openPrice, openPrice) == 0 &&
            Double.compare(candlestick.closePrice, closePrice) == 0 &&
            Double.compare(candlestick.lowPrice, lowPrice) == 0 &&
            Double.compare(candlestick.highPrice, highPrice) == 0 &&
            openTime.equals(candlestick.openTime) &&
            closeTime.equals(candlestick.closeTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openTime, closeTime, openPrice, closePrice, lowPrice, highPrice);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Candlestick.class.getSimpleName() + "[", "]")
            .add("openTime=" + openTime)
            .add("closeTime=" + closeTime)
            .add("openPrice=" + openPrice)
            .add("closePrice=" + closePrice)
            .add("lowPrice=" + lowPrice)
            .add("highPrice=" + highPrice)
            .toString();
    }
}
