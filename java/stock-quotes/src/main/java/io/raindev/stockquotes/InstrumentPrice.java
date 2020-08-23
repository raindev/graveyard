package io.raindev.stockquotes;

import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

public class InstrumentPrice {
    private final String isin;
    private final String description;
    // Note: using floating numbers for prices is only safe if they're not used in arithmetics.
    // For accounting precision BigInteger or integer fractions have to be used instead.
    private final Optional<Double> price;

    public InstrumentPrice(String isin, String description, Optional<Double> price) {
        this.isin = isin;
        this.description = description;
        this.price = price;
    }

    public String getIsin() {
        return isin;
    }

    public String getDescription() {
        return description;
    }

    public Optional<Double> getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstrumentPrice that = (InstrumentPrice) o;
        return isin.equals(that.isin) &&
            description.equals(that.description) &&
            price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isin, description, price);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", InstrumentPrice.class.getSimpleName() + "[", "]")
            .add("isin='" + isin + "'")
            .add("description='" + description + "'")
            .add("price=" + price)
            .toString();
    }

}
