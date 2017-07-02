package com.swarmmanager.docker.api.services.parameters;

import com.swarmmanager.docker.api.common.FilterParameter;
import com.swarmmanager.docker.api.common.FilterParameterValue;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class ServicesFilters {

    private FilterParameter id;

    private FilterParameter label;

    private FilterParameter name;

    public ServicesFilters setId(String id) {
        this.id = new FilterParameter("id").addValue(new FilterParameterValue(id));
        return this;
    }

    public ServicesFilters setLabel(String label) {
        this.label = new FilterParameter("label").addValue(new FilterParameterValue(label));
        return this;
    }

    public ServicesFilters setName(String name) {
        this.name = new FilterParameter("name").addValue(new FilterParameterValue(name));
        return this;
    }

    public String getJson() {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder();
        if (id != null) {
            id.addFilterJson(jsonObjectBuilder);
        }
        if (label != null) {
            label.addFilterJson(jsonObjectBuilder);
        }
        if (name != null) {
            name.addFilterJson(jsonObjectBuilder);
        }
        return jsonObjectBuilder.build().toString();
    }
}
