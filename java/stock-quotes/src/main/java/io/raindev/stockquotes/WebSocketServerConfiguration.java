package io.raindev.stockquotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketServerConfiguration implements WebSocketConfigurer {

    @Autowired HotInstrumentWebSocketHandler hotInstrumentsProducer;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(hotInstrumentsProducer, "/hot-instruments");
    }

    @Bean
    HotInstrumentWebSocketHandler hotInstrumentsProducer(ObjectMapper objectMapper) {
        return new HotInstrumentWebSocketHandler(objectMapper);
    }

}
