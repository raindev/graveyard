package io.raindev.stockquotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication
class Application {

    private final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CurrentPrices currentPrices() {
        return new CurrentPrices();
    }

    @Bean
    JsonWebSocketConsumer<InstrumentMessage> instrumentConsumer(CurrentPrices currentPrices,
                                                                ObjectMapper objectMapper) {
        return new JsonWebSocketConsumer<>(InstrumentMessage.class, objectMapper,
            Arrays.asList(
                message -> log.trace("Instrument message received: " + message),
                currentPrices::handle));
    }

    @Bean
    JsonWebSocketConsumer<QuoteMessage> quoteConsumer(CurrentPrices currentPrices,
                                                      ObjectMapper objectMapper) {
        return new JsonWebSocketConsumer<>(QuoteMessage.class, objectMapper,
            Arrays.asList(
                message -> log.trace("Quote received: " + message),
                currentPrices::handle));
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
