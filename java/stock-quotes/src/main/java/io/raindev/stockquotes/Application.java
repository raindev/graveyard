package io.raindev.stockquotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import java.time.Clock;
import java.util.Arrays;

@SpringBootApplication
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CurrentPrices currentPrices() {
        return new CurrentPrices();
    }

    @Bean
    PriceHistory priceHistory() {
        return new PriceHistory(Clock::systemUTC);
    }

    @Bean
    HotInstrumentDetector hotInstrumentDetector(HotInstrumentWebSocketHandler webSocketHandler) {
        return new HotInstrumentDetector(webSocketHandler::send);
    }

    @Bean
    JsonWebSocketConsumer<InstrumentMessage> instrumentConsumer(CurrentPrices currentPrices,
                                                                PriceHistory priceHistory,
                                                                ObjectMapper objectMapper) {
        return new JsonWebSocketConsumer<>(InstrumentMessage.class, objectMapper,
            Arrays.asList(
                currentPrices::handle,
                priceHistory::handle));
    }

    @Bean
    JsonWebSocketConsumer<QuoteMessage> quoteConsumer(CurrentPrices currentPrices,
                                                      PriceHistory priceHistory,
                                                      ObjectMapper objectMapper,
                                                      HotInstrumentDetector hotInstrumentDetector) {
        return new JsonWebSocketConsumer<>(QuoteMessage.class, objectMapper,
            Arrays.asList(
                currentPrices::handle,
                priceHistory::handle,
                hotInstrumentDetector::handle));
    }

    @Bean("instrumentConnectionManager")
    WebSocketConnectionManager instrumentConnectionManager(
        @Value("${sources.instruments.uri}") String sourceUri,
        JsonWebSocketConsumer<InstrumentMessage> instrumentConsumer) {
        final var webSocketConnectionManager = new WebSocketConnectionManager(
            new StandardWebSocketClient(), instrumentConsumer, sourceUri);
        webSocketConnectionManager.setAutoStartup(true);
        return webSocketConnectionManager;
    }

    @Bean("quoteConnectionManager")
    WebSocketConnectionManager quotesConnectionManager(
        @Value("${sources.quotes.uri}") String sourceUri,
        JsonWebSocketConsumer<QuoteMessage> quoteConsumer) {
        final var webSocketConnectionManager = new WebSocketConnectionManager(
            new StandardWebSocketClient(), quoteConsumer, sourceUri);
        webSocketConnectionManager.setAutoStartup(true);
        return webSocketConnectionManager;
    }

}
