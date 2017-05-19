package com.swarmmanager.docker.api.common.util;

import com.swarmmanager.docker.api.common.annotation.DockerFilterParam;
import org.apache.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;

public class JsonEncoder {

    private static final Logger LOGGER = Logger.getLogger(JsonEncoder.class.getName());

    public static String encodeJson(String json) {
        try {
            return URLEncoder.encode(json, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Error encoding json", e);
        }
        return null;
    }

    public static String toDockerJsonFilters(Object object) {
        StringBuilder builder = new StringBuilder("{");
        boolean addedComma = false;
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(DockerFilterParam.class)) {
                try {
                    if (!field.isAccessible()) {
                        field.setAccessible(true);
                    }
                    Object fieldValue = field.get(object);
                    if (fieldValue != null) {
                        builder.append("\"");
                        DockerFilterParam param = field.getAnnotation(DockerFilterParam.class);
                        String name = param.name();
                        if (name.equals("")) {
                            builder.append(field.getName());
                        } else {
                            builder.append(name);
                        }
                        builder.append("\":{\"").append(fieldValue).append("\":true},");
                        addedComma = true;
                    }
                } catch (IllegalAccessException e) {
                    LOGGER.error("Error encoding param " + field.getName(), e);
                }
            }
        }
        if (addedComma) {
            builder.deleteCharAt(builder.toString().length() - 1);
        }
        builder.append("}");
        return builder.toString();
    }

}
