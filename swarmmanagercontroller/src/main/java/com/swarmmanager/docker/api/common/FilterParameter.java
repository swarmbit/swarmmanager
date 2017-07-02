package com.swarmmanager.docker.api.common;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;
import java.util.List;

public class FilterParameter {

    private String name;

    private List<FilterParameterValue> values;

    public FilterParameter(String name) {
        this.name = name;
    }

    public FilterParameter addValue(FilterParameterValue value) {
        if (values == null) {
            values = new ArrayList<>();
        }
        values.add(value);
        return this;
    }

    public FilterParameter addFilterJson(JsonObjectBuilder jsonBuilder) {
        if (name != null && values != null) {
            JsonObjectBuilder valuesJsonBuilder = Json.createObjectBuilder();
            for (FilterParameterValue value : values) {
                valuesJsonBuilder.add(value.getValue(), !value.isBool());
            }
            JsonObject valuesObject = valuesJsonBuilder.build();
            jsonBuilder.add(name, valuesObject);
        }
        return this;
    }

}
