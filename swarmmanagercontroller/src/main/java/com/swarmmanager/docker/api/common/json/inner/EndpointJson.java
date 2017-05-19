package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class EndpointJson implements DockerRemoteApiJson {

    @JsonProperty("Spec")
    private EndpointSpecJson spec;

    @JsonProperty("Ports")
    private PortConfigJson[] ports;

    @JsonProperty("VirtualIPs")
    private EndpointVirtualIPJson[] virtualIPs;

    public EndpointSpecJson getSpec() {
        return spec;
    }

    public void setSpec(EndpointSpecJson spec) {
        this.spec = spec;
    }

    public PortConfigJson[] getPorts() {
        return ports;
    }

    public void setPorts(PortConfigJson[] ports) {
        this.ports = ports;
    }

    public EndpointVirtualIPJson[] getVirtualIPs() {
        return virtualIPs;
    }

    public void setVirtualIPs(EndpointVirtualIPJson[] virtualIPs) {
        this.virtualIPs = virtualIPs;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EndpointJson{");
        sb.append("spec=").append(spec);
        sb.append(", ports=").append(Arrays.toString(ports));
        sb.append(", virtualIPs=").append(virtualIPs);
        sb.append('}');
        return sb.toString();
    }
}
