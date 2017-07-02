package com.swarmmanager.docker.api.secrets.parameters;


import com.swarmmanager.docker.api.common.FilterParameter;
import com.swarmmanager.docker.api.common.FilterParameterValue;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

public class SecretsFilters {

    private FilterParameter id;

    private FilterParameter name;

    private FilterParameter label;

    public SecretsFilters setId(String id) {
        this.name = new FilterParameter("id").addValue(new FilterParameterValue(id));
        return this;
    }

    public SecretsFilters setName(String name) {
        this.name = new FilterParameter("name").addValue(new FilterParameterValue(name));
        return this;
    }

    public SecretsFilters setLabel(String label) {
        this.label = new FilterParameter("label").addValue(new FilterParameterValue(label));
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
        return jsonObjectBuilder.build().toString();
    }

}
