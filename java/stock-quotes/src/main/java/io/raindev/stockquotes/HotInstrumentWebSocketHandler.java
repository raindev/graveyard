package io.raindev.stockquotes;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class HotInstrumentWebSocketHandler extends TextWebSocketHandler {
    private static final Logger log = LoggerFactory.getLogger(HotInstrumentWebSocketHandler.class);

    private final Map<String, WebSocketSession> sessionsById = new ConcurrentHashMap<>();
    private final ObjectMapper jsonMapper;

    HotInstrumentWebSocketHandler(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    void send(Object event) {
        for (var session : sessionsById.values()) {
            try {
                session.sendMessage(new TextMessage(jsonMapper.writeValueAsString(event)));
            } catch (IOException e) {
                log.error("Failed to send hot instrument event to the user", e);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessionsById.put(session.getId(), session);
        log.info("Client connected, total {}", sessionsById.size());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessionsById.remove(session.getId());
        log.info("Client disconnected, total {}", sessionsById.size());
    }

}
