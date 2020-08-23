package io.raindev.stockquotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class InstrumentController {

    @Autowired CurrentPrices currentPrices;
    @Autowired PriceHistory priceHistory;

    @RequestMapping("/instruments")
    Collection<InstrumentPrice> list() {
        return currentPrices.list();
    }

    @RequestMapping("/instruments/{isin}/history")
    Collection<Candlestick> priceHistory(@PathVariable String isin) {
        return priceHistory.forInstrument(isin);
    }

}
