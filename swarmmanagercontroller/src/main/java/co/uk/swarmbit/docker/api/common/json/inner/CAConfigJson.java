package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import co.uk.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CAConfigJson {

    @JsonProperty("NodeCertExpiry")
    private Long nodeCertExpiry;

    @JsonProperty("ExternalCAs")
    private ExternalCAJson[] externalCAs;

    @DockerRemoteApiMinVersion("v1.30")
    @JsonProperty("SigningCACert")
    private String signingCACert;

    @DockerRemoteApiMinVersion("v1.30")
    @JsonProperty("SigningCAKey")
    private String signingCAKey;

    @DockerRemoteApiMinVersion("v1.30")
    @JsonProperty("ForceRotate")
    private Integer forceRotate;

    public Long getNodeCertExpiry() {
        return nodeCertExpiry;
    }

    public void setNodeCertExpiry(Long nodeCertExpiry) {
        this.nodeCertExpiry = nodeCertExpiry;
    }

    public ExternalCAJson[] getExternalCAs() {
        return externalCAs;
    }

    public void setExternalCAs(ExternalCAJson[] externalCAs) {
        this.externalCAs = externalCAs;
    }

    public String getSigningCACert() {
        return signingCACert;
    }

    public void setSigningCACert(String signingCACert) {
        this.signingCACert = signingCACert;
    }

    public String getSigningCAKey() {
        return signingCAKey;
    }

    public void setSigningCAKey(String signingCAKey) {
        this.signingCAKey = signingCAKey;
    }

    public Integer getForceRotate() {
        return forceRotate;
    }

    public void setForceRotate(Integer forceRotate) {
        this.forceRotate = forceRotate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CAConfigJson{");
        sb.append("nodeCertExpiry='").append(nodeCertExpiry).append('\'');
        sb.append(", externalCAs=").append(Arrays.toString(externalCAs));
        sb.append(", signingCACert='").append(signingCACert).append('\'');
        sb.append(", signingCAKey='").append(signingCAKey).append('\'');
        sb.append(", forceRotate=").append(forceRotate);
        sb.append('}');
        return sb.toString();
    }
}
