package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NetworkAttachmentConfigJson {

    @JsonProperty("Target")
    private String target;

    @JsonProperty("Aliases")
    private String[] aliases;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String[] getAliases() {
        return aliases;
    }

    public void setAliases(String[] aliases) {
        this.aliases = aliases;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NetworkAttachmentConfigJson{");
        sb.append("target='").append(target).append('\'');
        sb.append(", aliases=").append(Arrays.toString(aliases));
        sb.append('}');
        return sb.toString();
    }
}
