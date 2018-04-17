package co.uk.swarmbit.docker.api.common.json;

import co.uk.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion;
import co.uk.swarmbit.docker.api.common.json.inner.DriverJson;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DockerRemoteApiMinVersion("v1.25")
public class SecretSpecJson {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Labels")
    private Map<String, String> labels;

    @JsonProperty("Data")
    private String data;

    @DockerRemoteApiMinVersion("v1.31")
    @JsonProperty("Driver")
    private DriverJson driver;

    @DockerRemoteApiMinVersion("v1.37")
    @JsonProperty("Templating")
    private DriverJson templating;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public DriverJson getDriver() {
        return driver;
    }

    public void setDriver(DriverJson driver) {
        this.driver = driver;
    }

    public DriverJson getTemplating() {
        return templating;
    }

    public void setTemplating(DriverJson templating) {
        this.templating = templating;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SecretSpecJson{");
        sb.append("name='").append(name).append('\'');
        sb.append(", labels=").append(labels);
        sb.append(", data='").append(data).append('\'');
        sb.append(", driver=").append(driver);
        sb.append(", templating=").append(templating);
        sb.append('}');
        return sb.toString();
    }
}
