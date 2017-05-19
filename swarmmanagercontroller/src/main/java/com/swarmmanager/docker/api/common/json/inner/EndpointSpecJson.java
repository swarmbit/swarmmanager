package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class EndpointSpecJson implements DockerRemoteApiJson {

    @JsonProperty("Mode")
    private String mode;

    @JsonProperty("Ports")
    private PortConfigJson[] ports;

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public PortConfigJson[]  getPorts() {
        return ports;
    }

    public void setPorts(PortConfigJson[] ports) {
        this.ports = ports;
    }

}
