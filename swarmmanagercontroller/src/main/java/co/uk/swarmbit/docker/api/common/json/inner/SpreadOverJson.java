package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpreadOverJson {

    @JsonProperty("SpreadDescriptor")
    private String spreadDescriptor;

    public String getSpreadDescriptor() {
        return spreadDescriptor;
    }

    public void setSpreadDescriptor(String spreadDescriptor) {
        this.spreadDescriptor = spreadDescriptor;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SpreadOverJson{");
        sb.append("spreadDescriptor='").append(spreadDescriptor).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
