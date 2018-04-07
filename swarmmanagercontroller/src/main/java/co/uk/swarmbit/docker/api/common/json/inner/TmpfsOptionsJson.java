package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TmpfsOptionsJson {

    @JsonProperty("SizeBytes")
    private Long sizeBytes;

    @JsonProperty("Mode")
    private Integer mode;

    public Long getSizeBytes() {
        return sizeBytes;
    }

    public void setSizeBytes(Long sizeBytes) {
        this.sizeBytes = sizeBytes;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TmpfsOptionsJson{");
        sb.append("sizeBytes=").append(sizeBytes);
        sb.append(", mode=").append(mode);
        sb.append('}');
        return sb.toString();
    }
}
