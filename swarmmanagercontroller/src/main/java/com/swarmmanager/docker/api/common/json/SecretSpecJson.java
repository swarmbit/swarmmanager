package com.swarmmanager.docker.api.common.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiMinVersion("v1.25")
public class SecretSpecJson {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Labels")
    private Map<String, String> labels;

    @JsonProperty("Data")
    private String data;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SecretSpecJson{");
        sb.append("name='").append(name).append('\'');
        sb.append(", labels=").append(labels);
        sb.append(", data='").append(data).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
