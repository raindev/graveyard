package io.raindev.stockquotes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Configuration;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@JsonTest
class QuoteMessageTest {

    @Configuration
    static class TestConfiguration {
    }

    @Autowired ObjectMapper jsonMapper;

    @Test
    void parseFromJson() throws JsonProcessingException {
        var json = "{" +
            "  \"data\": {" +
            "    \"price\": 99.99," +
            "    \"isin\": \"isin\"" +
            "  }," +
            "  \"type\": \"QUOTE\"" +
            "}";

        assertEquals(
            jsonMapper.readValue(json, QuoteMessage.class),
            new QuoteMessage(QuoteMessage.Type.QUOTE,
                new QuoteMessage.Data(99.99, "isin")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"data", "type"})
    void requireAllFields(String fieldName) throws JsonProcessingException {
        var fullJson = "{" +
            "  \"data\": {" +
            "    \"price\": 5.70," +
            "    \"isin\": \"ISIN\"" +
            "  }," +
            "  \"type\": \"QUOTE\"" +
            "}";
        var jsonObject = (ObjectNode) jsonMapper.readTree(fullJson);
        jsonObject.remove(fieldName);
        var jsonWithoutField = jsonObject.toString();

        assertThrows(JsonMappingException.class, () -> jsonMapper.readValue(jsonWithoutField,
            QuoteMessage.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"price", "isin"})
    void requireAllDataFields(String fieldName) throws JsonProcessingException {
        var fullJson = "{" +
            "  \"data\": {" +
            "    \"price\": 5.70," +
            "    \"isin\": \"ISIN\"" +
            "  }," +
            "  \"type\": \"QUOTE\"" +
            "}";
        var jsonObject = (ObjectNode) jsonMapper.readTree(fullJson);
        ((ObjectNode) jsonObject.get("data")).remove(fieldName);
        var jsonWithoutField = jsonObject.toString();

        assertThrows(JsonMappingException.class, () -> jsonMapper.readValue(jsonWithoutField,
            QuoteMessage.class));
    }

}
