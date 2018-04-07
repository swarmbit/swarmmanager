package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VolumeOptionsJson {

    @JsonProperty("NoCopy")
    private Boolean noCopy;

    @JsonProperty("Labels")
    private Map<String, String> labels;

    @JsonProperty("DriverConfig")
    private DriverJson driver;

    public Boolean isNoCopy() {
        return noCopy;
    }

    public void setNoCopy(Boolean noCopy) {
        this.noCopy = noCopy;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public DriverJson getDriver() {
        return driver;
    }

    public void setDriver(DriverJson driver) {
        this.driver = driver;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VolumeOptionsJson{");
        sb.append("noCopy=").append(noCopy);
        sb.append(", labels=").append(labels);
        sb.append(", driver=").append(driver);
        sb.append('}');
        return sb.toString();
    }
}
