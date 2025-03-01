package com.example.maxbitdigitalasset.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

@Slf4j
public class JsonUtil {
    private JsonUtil() {
        throw new IllegalArgumentException();
    }

    public static String objectToJson(Object target) {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);

        if (ObjectUtils.isEmpty(target)) return null;

        try {
            return mapper.writeValueAsString(target);
        } catch (JsonProcessingException ex) {
            log.info("Cannot pars to json but return toString() value instead", ex);
            return target.toString();
        }
    }
}
