package io.raindev.stockquotes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.StringJoiner;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
final class InstrumentMessage {
    enum Type {ADD, DELETE}

    @JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NON_PRIVATE)
    static final class Data {

        private final String description;
        private final String isin;

        Data(@JsonProperty(value = "description", required = true) String description,
             @JsonProperty(value = "isin", required = true) String isin) {
            this.description = description;
            this.isin = isin;
        }

        String getDescription() {
            return description;
        }

        String getIsin() {
            return isin;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Data data = (Data) o;
            return description.equals(data.description) &&
                isin.equals(data.isin);
        }

        @Override
        public int hashCode() {
            return Objects.hash(description, isin);
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Data.class.getSimpleName() + "[", "]")
                .add("description='" + description + "'")
                .add("isin='" + isin + "'")
                .toString();
        }
    }

    private final Type type;
    private final Data data;

    InstrumentMessage(@JsonProperty(value = "type", required = true) Type type,
                      @JsonProperty(value = "data", required = true) Data data) {
        this.type = type;
        this.data = data;
    }

    Data getData() {
        return data;
    }

    Type getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstrumentMessage that = (InstrumentMessage) o;
        return type == that.type &&
            data.equals(that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, data);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", InstrumentMessage.class.getSimpleName() + "[", "]")
            .add("type=" + type)
            .add("data=" + data)
            .toString();
    }

}
