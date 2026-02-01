package com.booking.config;

import com.booking.error.ErrorResponse;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.YamlMapFactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class ErrorConfig implements InitializingBean {

    private Map<String, Map<String, Map<String, Object>>> errors = Collections.emptyMap();

    @Override
    public void afterPropertiesSet() {
        YamlMapFactoryBean yaml = new YamlMapFactoryBean();
        yaml.setResources(new ClassPathResource("errors.yml"));
        Map<?, ?> loaded = yaml.getObject();
        if (loaded != null) {
            //noinspection unchecked
            errors = (Map<String, Map<String, Map<String, Object>>>) loaded.get("errors");
        }
    }

    /**
     * Fetch an error by category and key
     */
    public ErrorResponse getError(String category, String key) {
        Map<String, Map<String, Object>> categoryMap = errors.get(category);
        if (categoryMap == null) {
            return new ErrorResponse(9999, "Unknown error category", List.of());
        }
        Map<String, Object> e = categoryMap.get(key);
        if (e == null) {
            return new ErrorResponse(9999, "Unknown error key", List.of());
        }
        int code = (Integer) e.get("code");
        String message = (String) e.get("message");
        return new ErrorResponse(code, message, List.of());
    }
}