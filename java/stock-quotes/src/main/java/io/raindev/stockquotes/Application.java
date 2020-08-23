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
    InstrumentSource sourceInstruments(ObjectMapper objectMapper) {
        return new InstrumentSource(objectMapper, Collections.emptyList());
    }

    @Bean
    WebSocketConnectionManager instrumentSourceConnectionManager(
        @Value("${sources.instruments.uri}") String sourceUri, InstrumentSource instrumentSource) {
        final var webSocketConnectionManager = new WebSocketConnectionManager(
            new StandardWebSocketClient(), instrumentSource, sourceUri);
        webSocketConnectionManager.setAutoStartup(true);
        return webSocketConnectionManager;
    }

}
