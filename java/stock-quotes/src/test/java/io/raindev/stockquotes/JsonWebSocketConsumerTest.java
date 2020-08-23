package io.raindev.stockquotes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class JsonWebSocketConsumerTest {

    ObjectMapper jsonMapper = new ObjectMapper();

    @Test
    void parseAndForwardMessage() throws JsonProcessingException {
        var message = new InstrumentMessage(InstrumentMessage.Type.ADD,
            new InstrumentMessage.Data("description", "isin"));
        var messageReceived = new AtomicBoolean(false);
        var source = new JsonWebSocketConsumer<>(
            InstrumentMessage.class,
            jsonMapper,
            Collections.singleton(m -> {
                assertEquals(message, m, "the original message should be forwarded");
                messageReceived.set(true);
            }));

        source.handleTextMessage(mock(WebSocketSession.class),
            new TextMessage(jsonMapper.writeValueAsString(message)));

        assertTrue(messageReceived.get(), "the message should be forwarded");
    }

    @Test
    void forwardMessageToAllHandlers() throws JsonProcessingException {
        var firstHandleMessageReceived = new AtomicBoolean(false);
        var secondHandlerMessageReceived = new AtomicBoolean(false);
        var message = new InstrumentMessage(InstrumentMessage.Type.ADD,
            new InstrumentMessage.Data("description", "isin"));
        var source = new JsonWebSocketConsumer<>(InstrumentMessage.class, jsonMapper, Arrays.asList(
            m -> firstHandleMessageReceived.set(true),
            m -> secondHandlerMessageReceived.set(true)
        ));

        source.handleTextMessage(mock(WebSocketSession.class),
            new TextMessage(jsonMapper.writeValueAsString(message)));

        assertTrue(firstHandleMessageReceived.get(), "the first handler should get the message");
        assertTrue(secondHandlerMessageReceived.get(), "the second handler should get the message");
    }

    @Test
    void forwardAllMessages() throws JsonProcessingException {
        var forwardedMessageCount = new AtomicInteger(0);
        var message = new QuoteMessage(QuoteMessage.Type.QUOTE,
            new QuoteMessage.Data("isin", 22.53));
        var source = new JsonWebSocketConsumer<>(QuoteMessage.class, jsonMapper,
            Collections.singleton(
                m -> forwardedMessageCount.incrementAndGet()
            ));

        var messageJson = jsonMapper.writeValueAsString(message);
        source.handleTextMessage(mock(WebSocketSession.class), new TextMessage(messageJson));
        source.handleTextMessage(mock(WebSocketSession.class), new TextMessage(messageJson));
        source.handleTextMessage(mock(WebSocketSession.class), new TextMessage(messageJson));

        assertEquals(3, forwardedMessageCount.get(), "all messages should be forwarded");
    }

    @Test
    void skipMalformedMessages() {
        new JsonWebSocketConsumer<>(QuoteMessage.class, jsonMapper, Collections.singleton(
            message -> fail("No message is expected")
        )).handleTextMessage(mock(WebSocketSession.class), new TextMessage("malformedJsonMessage"));
    }

}
