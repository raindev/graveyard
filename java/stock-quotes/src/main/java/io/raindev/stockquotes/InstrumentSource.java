package io.raindev.stockquotes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

final class InstrumentSource extends TextWebSocketHandler {

    private static final Logger log = LoggerFactory.getLogger(InstrumentSource.class);
    private final ObjectMapper jsonMapper;
    private final Iterable<InstrumentMessageHandler> messageHandlers;

    InstrumentSource(ObjectMapper jsonMapper, Iterable<InstrumentMessageHandler> messageHandlers) {
        this.jsonMapper = jsonMapper;
        this.messageHandlers = messageHandlers;
    }

    @Override
    protected void handleTextMessage(@NonNull WebSocketSession session, TextMessage message) {
        log.trace("Instrument message received:\n" + message.getPayload());
        try {
            final var parsedMessage = jsonMapper.readValue(message.getPayload(),
                InstrumentMessage.class);
            messageHandlers.forEach(h -> h.handle(parsedMessage));
        } catch (JsonProcessingException e) {
            log.error("Failed to parse instrument message", e);
        }
    }

    @Override
    public void afterConnectionEstablished(@NonNull WebSocketSession session) {
        log.debug("Connection to instrument source established");
    }

    @Override
    public void handleTransportError(@NonNull WebSocketSession session,
                                     @NonNull Throwable exception) {
        log.error("Instrument source connection error", exception);
    }

}
