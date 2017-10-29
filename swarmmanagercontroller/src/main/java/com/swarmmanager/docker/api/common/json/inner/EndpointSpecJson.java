package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EndpointSpecJson {

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
