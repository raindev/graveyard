package io.raindev.stockquotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.client.WebSocketConnectionManager;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.Collections;

@SpringBootApplication
class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    JsonWebSocketConsumer<InstrumentMessage> instrumentConsumer(ObjectMapper objectMapper) {
        return new JsonWebSocketConsumer<>(InstrumentMessage.class, objectMapper,
            Collections.emptyList());
    }

    @Bean
    WebSocketConnectionManager instrumentConnectionManager(
        @Value("${sources.instruments.uri}") String sourceUri,
        JsonWebSocketConsumer<InstrumentMessage> instrumentConsumer) {
        final var webSocketConnectionManager = new WebSocketConnectionManager(
            new StandardWebSocketClient(), instrumentConsumer, sourceUri);
        webSocketConnectionManager.setAutoStartup(true);
        return webSocketConnectionManager;
    }

}
