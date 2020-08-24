package io.raindev.stockquotes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class HotInstrumentMessage {
    private final String isin;
    private final double changePercents;
    private final double price;

    public HotInstrumentMessage(String isin, double changePercents, double price) {
        this.isin = isin;
        this.changePercents = changePercents;
        this.price = price;
    }
}
