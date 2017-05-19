package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class CAConfigJson implements DockerRemoteApiJson {

    @JsonProperty("NodeCertExpiry")
    private String nodeCertExpiry;

    @JsonProperty("ExternalCAs")
    private ExternalCAJson[] externalCAs;

    public String getNodeCertExpiry() {
        return nodeCertExpiry;
    }

    public void setNodeCertExpiry(String nodeCertExpiry) {
        this.nodeCertExpiry = nodeCertExpiry;
    }

    public ExternalCAJson[] getExternalCAs() {
        return externalCAs;
    }

    public void setExternalCAs(ExternalCAJson[] externalCAs) {
        this.externalCAs = externalCAs;
    }
}
