package com.swarmmanager.docker.api.common.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.json.inner.ManagerStatusJson;
import com.swarmmanager.docker.api.common.json.inner.NodeDescriptionJson;
import com.swarmmanager.docker.api.common.json.inner.NodeStatusJson;
import com.swarmmanager.docker.api.common.json.inner.VersionJson;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class NodeJson implements DockerRemoteApiJson {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Version")
    private VersionJson version;

    @JsonProperty("CreatedAt")
    private String createdAt;

    @JsonProperty("UpdatedAt")
    private String updatedAt;

    @JsonProperty("Spec")
    private NodeSpecJson spec;

    @JsonProperty("Description")
    private NodeDescriptionJson description;

    @JsonProperty("Status")
    private NodeStatusJson status;

    @JsonProperty("ManagerStatus")
    private ManagerStatusJson managerStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VersionJson getVersion() {
        return version;
    }

    public void setVersion(VersionJson version) {
        this.version = version;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public NodeSpecJson getSpec() {
        return spec;
    }

    public void setSpec(NodeSpecJson spec) {
        this.spec = spec;
    }

    public NodeDescriptionJson getDescription() {
        return description;
    }

    public void setDescription(NodeDescriptionJson description) {
        this.description = description;
    }

    public NodeStatusJson getStatus() {
        return status;
    }

    public void setStatus(NodeStatusJson status) {
        this.status = status;
    }

    public ManagerStatusJson getManagerStatus() {
        return managerStatus;
    }

    public void setManagerStatus(ManagerStatusJson managerStatus) {
        this.managerStatus = managerStatus;
    }
}
