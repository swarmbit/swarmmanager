package com.swarmmanager.docker.api.common.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.json.inner.EndpointJson;
import com.swarmmanager.docker.api.common.json.inner.UpdateStatusJson;
import com.swarmmanager.docker.api.common.json.inner.VersionJson;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceJson {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Version")
    private VersionJson version;

    @JsonProperty("CreatedAt")
    private String createdAt;

    @JsonProperty("UpdatedAt")
    private String updatedAt;

    @JsonProperty("Spec")
    private ServiceSpecJson spec;

    @JsonProperty("PreviousSpec")
    private ServiceSpecJson previousSpec;

    @JsonProperty("Endpoint")
    private EndpointJson endpoint;

    @JsonProperty("UpdateStatus")
    private UpdateStatusJson updateStatus;

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

    public ServiceSpecJson getSpec() {
        return spec;
    }

    public void setSpec(ServiceSpecJson spec) {
        this.spec = spec;
    }

    public ServiceSpecJson getPreviousSpec() {
        return previousSpec;
    }

    public void setPreviousSpec(ServiceSpecJson previousSpec) {
        this.previousSpec = previousSpec;
    }

    public EndpointJson getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(EndpointJson endpoint) {
        this.endpoint = endpoint;
    }

    public UpdateStatusJson getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(UpdateStatusJson updateStatus) {
        this.updateStatus = updateStatus;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ServiceJson{");
        sb.append("id='").append(id).append('\'');
        sb.append(", version=").append(version);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", spec=").append(spec);
        sb.append(", previousSpec=").append(previousSpec);
        sb.append(", endpoint=").append(endpoint);
        sb.append(", updateStatus=").append(updateStatus);
        sb.append('}');
        return sb.toString();
    }
}