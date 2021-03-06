package io.raindev.stockquotes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.socket.client.WebSocketConnectionManager;

@SpringBootTest
class ApplicationTest {

    @MockBean(name = "instrumentConnectionManager")
    WebSocketConnectionManager instrumentConnectionManager;

    @MockBean(name = "quoteConnectionManager")
    WebSocketConnectionManager quoteConnectionManager;

    @Test
    void contextLoads() {
    }

}
