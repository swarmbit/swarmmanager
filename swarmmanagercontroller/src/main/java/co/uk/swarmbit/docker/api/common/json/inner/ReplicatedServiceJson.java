package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReplicatedServiceJson {

    @JsonProperty("Replicas")
    private Long replicas;

    public Long getReplicas() {
        return replicas;
    }

    public void setReplicas(Long replicas) {
        this.replicas = replicas;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReplicatedServiceJson{");
        sb.append("replicas=").append(replicas);
        sb.append('}');
        return sb.toString();
    }
}
