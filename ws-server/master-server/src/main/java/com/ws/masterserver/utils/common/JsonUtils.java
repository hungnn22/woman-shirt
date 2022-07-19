package com.ws.masterserver.utils.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtils {
    private JsonUtils() {}

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String toJson(Object value) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            jsonString = "Can't build json from object";
        }
        return jsonString;
    }

    public static <T> T fromJson(String jsonString, Class<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(jsonString, type);
        } catch (JsonProcessingException e) {
            log.error("Can't parse json to object: {}", jsonString);
        }
        return null;
    }
}
