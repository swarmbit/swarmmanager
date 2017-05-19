package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class PlacementJson implements DockerRemoteApiJson {

    @JsonProperty("Constraints")
    private String[] constraints;

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
