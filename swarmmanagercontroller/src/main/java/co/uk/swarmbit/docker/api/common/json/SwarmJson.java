package co.uk.swarmbit.docker.api.common.json;

import co.uk.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion;
import co.uk.swarmbit.docker.api.common.json.inner.JoinTokensJson;
import co.uk.swarmbit.docker.api.common.json.inner.TLSInfoJson;
import co.uk.swarmbit.docker.api.common.json.inner.VersionJson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SwarmJson {

    @JsonProperty("ID")
    private String id;

    @JsonProperty("Version")
    private VersionJson version;

    @JsonProperty("CreatedAt")
    private String createdAt;

    @JsonProperty("UpdatedAt")
    private String updatedAt;

    @JsonProperty("Spec")
    private SwarmSpecJson spec;

    @JsonProperty("JoinTokens")
    private JoinTokensJson joinTokens;

    @DockerRemoteApiMinVersion("v1.30")
    @JsonProperty("RootRotationInProgress")
    private Boolean rootRotationInProgress;

    @DockerRemoteApiMinVersion("v1.30")
    @JsonProperty("TLSInfo")
    private TLSInfoJson tlsInfo;

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

    public SwarmSpecJson getSpec() {
        return spec;
    }

    public void setSpec(SwarmSpecJson spec) {
        this.spec = spec;
    }

    public JoinTokensJson getJoinTokens() {
        return joinTokens;
    }

    public void setJoinTokens(JoinTokensJson joinTokens) {
        this.joinTokens = joinTokens;
    }

    public Boolean isRootRotationInProgress() {
        return rootRotationInProgress;
    }

    public void setRootRotationInProgress(Boolean rootRotationInProgress) {
        this.rootRotationInProgress = rootRotationInProgress;
    }

    public TLSInfoJson getTlsInfo() {
        return tlsInfo;
    }

    public void setTlsInfo(TLSInfoJson tlsInfo) {
        this.tlsInfo = tlsInfo;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SwarmJson{");
        sb.append("id='").append(id).append('\'');
        sb.append(", version=").append(version);
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", updatedAt='").append(updatedAt).append('\'');
        sb.append(", spec=").append(spec);
        sb.append(", joinTokens=").append(joinTokens);
        sb.append(", rootRotationInProgress=").append(rootRotationInProgress);
        sb.append(", tlsInfo=").append(tlsInfo);
        sb.append('}');
        return sb.toString();
    }
}
