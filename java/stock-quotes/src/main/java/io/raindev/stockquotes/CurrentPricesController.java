package io.raindev.stockquotes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
class CurrentPricesController {

    @Autowired CurrentPrices currentPrices;

    @RequestMapping("/instruments")
    Collection<InstrumentPrice> list() {
        return currentPrices.list();
    }
}
