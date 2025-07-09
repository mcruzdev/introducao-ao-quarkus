package tech.ada;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public interface JSON {


    static Product productFromJSON(String json) {
        // TODO: falar sobre o ObjectMapper e o JavaTimeModule
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        try {
            return objectMapper.readValue(json, Product.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    static String toJSON(Product product) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    static Address addressFromJSON(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            return objectMapper.readValue(json, Address.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
