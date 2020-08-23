package io.raindev.stockquotes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.context.annotation.Configuration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

// Will allow to make sure JSON parsing works as expected with actual ObjectMapper
// configuration (without bringing up the whole application).
@JsonTest
class InstrumentMessageTest {

    // Replace the application configuration with an empty one.
    @Configuration
    static class TestConfiguration {
    }

    @Autowired ObjectMapper objectMapper;

    @Test
    void parseFromJson() throws JsonProcessingException {
        var json = "{" +
            "  \"data\": {" +
            "    \"description\": \"description\"," +
            "    \"isin\": \"ISIN\"" +
            "  }," +
            "  \"type\": \"ADD\"" +
            "}";

        assertEquals(
            objectMapper.readValue(json, InstrumentMessage.class),
            new InstrumentMessage(InstrumentMessage.Type.ADD,
                new InstrumentMessage.Data("description", "ISIN")));
    }

    @ParameterizedTest
    @ValueSource(strings = {"data", "type"})
    void requireAllFields(String fieldName) throws JsonProcessingException {
        var fullJson = "{" +
            "  \"data\": {" +
            "    \"description\": \"description\"," +
            "    \"isin\": \"ISIN\"" +
            "  }," +
            "  \"type\": \"DELETE\"" +
            "}";
        var jsonObject = (ObjectNode) objectMapper.readTree(fullJson);
        jsonObject.remove(fieldName);
        var jsonWithoutField = jsonObject.toString();

        assertThrows(JsonMappingException.class, () -> objectMapper.readValue(jsonWithoutField,
            InstrumentMessage.class));
    }

    @ParameterizedTest
    @ValueSource(strings = {"description", "isin"})
    void requireAllDataFields(String fieldName) throws JsonProcessingException {
        var fullJson = "{" +
            "  \"data\": {" +
            "    \"description\": \"description\"," +
            "    \"isin\": \"ISIN\"" +
            "  }," +
            "  \"type\": \"DELETE\"" +
            "}";
        var jsonObject = (ObjectNode) objectMapper.readTree(fullJson);
        ((ObjectNode) jsonObject.get("data")).remove(fieldName);
        var jsonWithoutField = jsonObject.toString();

        assertThrows(JsonMappingException.class, () -> objectMapper.readValue(jsonWithoutField,
            InstrumentMessage.class));
    }

}
