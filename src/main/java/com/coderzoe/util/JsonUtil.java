package com.coderzoe.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author coderZoe
 * @date 2023/8/14 20:51
 */
public class JsonUtil<T> {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static <T> String parseToJson(T obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
