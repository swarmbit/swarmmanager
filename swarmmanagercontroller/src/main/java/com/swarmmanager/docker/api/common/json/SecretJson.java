package com.swarmmanager.docker.api.common.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;
import com.swarmmanager.docker.api.common.json.inner.VersionJson;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DockerRemoteApiMinVersion("v1.25")
public class SecretJson {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Version")
    private VersionJson version;

    @JsonProperty("CreatedAt")
    private String createdAt;

    @JsonProperty("UpdatedAt")
    private String updatedAt;

    @JsonProperty("Spec")
    private SecretSpecJson spec;

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

    public SecretSpecJson getSpec() {
        return spec;
    }

    public void setSpec(SecretSpecJson spec) {
        this.spec = spec;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SecretJson{");
        sb.append("id='").append(id).append('\'');
        sb.append(", version=").append(version);
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", updatedAt='").append(updatedAt).append('\'');
        sb.append(", spec=").append(spec);
        sb.append('}');
        return sb.toString();
    }
}
