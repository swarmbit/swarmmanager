package co.uk.swarmbit.docker.api.common.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NetworkCreateResponseJson {

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Warning")
    private String warning;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NetworkCreateResponseJson{");
        sb.append("id='").append(id).append('\'');
        sb.append(", warning='").append(warning).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
