package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverJson {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Name")
    private Map<String, String> options;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DriverJson{");
        sb.append("name='").append(name).append('\'');
        sb.append(", options=").append(options);
        sb.append('}');
        return sb.toString();
    }
}
