package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlacementJson {

    @JsonProperty("Constraints")
    private String[] constraints;

    @DockerRemoteApiMinVersion("v1.28")
    @JsonProperty("Preferences")
    private PreferencesJson[]  preferences ;

    @DockerRemoteApiMinVersion("v1.30")
    @JsonProperty("Platforms")
    private PlatformJson[] platforms;

    public String[] getConstraints() {
        return constraints;
    }

    public void setConstraints(String[] constraints) {
        this.constraints = constraints;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PlacementJson{");
        sb.append("constraints=").append(Arrays.toString(constraints));
        sb.append('}');
        return sb.toString();
    }
}
