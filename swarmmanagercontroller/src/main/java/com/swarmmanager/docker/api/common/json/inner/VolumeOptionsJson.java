package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class VolumeOptionsJson implements DockerRemoteApiJson {

    @JsonProperty("NoCopy")
    private boolean noCopy;

    @JsonProperty("Labels")
    private Map<String, String> labels;

    @JsonProperty("DriverConfig")
    private DriverJson driver;

    public boolean isNoCopy() {
        return noCopy;
    }

    public void setNoCopy(boolean noCopy) {
        this.noCopy = noCopy;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public DriverJson getDriver() {
        return driver;
    }

    public void setDriver(DriverJson driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VolumeOptionsJson{");
        sb.append("noCopy=").append(noCopy);
        sb.append(", labels=").append(labels);
        sb.append(", driver=").append(driver);
        sb.append('}');
        return sb.toString();
    }
}
