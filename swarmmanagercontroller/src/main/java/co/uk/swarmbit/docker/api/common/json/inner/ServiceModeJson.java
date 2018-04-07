package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ServiceModeJson {

    @JsonProperty("Replicated")
    private ReplicatedServiceJson replicated;

    @JsonProperty("Global")
    private GlobalServiceJson global;

    public ReplicatedServiceJson getReplicated() {
        return replicated;
    }

    public void setReplicated(ReplicatedServiceJson replicated) {
        this.replicated = replicated;
    }

    public GlobalServiceJson getGlobal() {
        return global;
    }

    public void setGlobal(GlobalServiceJson global) {
        this.global = global;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ServiceModeJson{");
        sb.append("replicated=").append(replicated);
        sb.append(", global=").append(global);
        sb.append('}');
        return sb.toString();
    }
}
