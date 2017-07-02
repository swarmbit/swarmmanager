package com.swarmmanager.docker.api.common;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractFilters implements Filters {

    private Map<String, List<String>> filterParameters;

    public AbstractFilters() {
        filterParameters = new HashMap<>();
    }

    public String getJson() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        if (filterParameters != null) {
            for (Map.Entry<String, List<String>> filterEntry : filterParameters.entrySet()) {
                if (filterEntry.getValue() != null) {
                    JsonObjectBuilder valuesJsonBuilder = Json.createObjectBuilder();
                    for (String value : filterEntry.getValue()) {
                        valuesJsonBuilder.add(value, true);
                    }
                    jsonObjectBuilder.add(filterEntry.getKey(), valuesJsonBuilder);
                }
            }
        }
        return jsonObjectBuilder.build().toString();
    }

    protected AbstractFilters addFilter(String key, String value) {
        if (value != null) {
            List<String> values = filterParameters.computeIfAbsent(key, k -> new ArrayList<>());
            values.add(value);
        }
        return this;
    }

}
