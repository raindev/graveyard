package io.raindev.stockquotes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.StringJoiner;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
public class QuoteMessage {
    enum Type {QUOTE}

    @JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
    static final class Data {

        private final double price;
        private final String isin;

        Data(@JsonProperty(value = "price", required = true) double price,
             @JsonProperty(value = "isin", required = true) String isin) {
            this.price = price;
            this.isin = isin;
        }

        public String getIsin() {
            return isin;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return Double.compare(data.price, price) == 0 &&
                isin.equals(data.isin);
        }

        @Override
        public int hashCode() {
            return Objects.hash(price, isin);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Data.class.getSimpleName() + "[", "]")
                .add("price=" + price)
                .add("isin='" + isin + "'")
                .toString();
        }
    }

    private final Type type;
    private final Data data;

    public QuoteMessage(@JsonProperty(value = "type", required = true) Type type,
                        @JsonProperty(value = "data", required = true) Data data) {
        this.type = type;
        this.data = data;
    }

    public Type getType() {
        return type;
    }

    public Data getData() {
        return data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuoteMessage that = (QuoteMessage) o;
        return type == that.type &&
            data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, data);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", QuoteMessage.class.getSimpleName() + "[", "]")
            .add("type=" + type)
            .add("data=" + data)
            .toString();
    }
}
