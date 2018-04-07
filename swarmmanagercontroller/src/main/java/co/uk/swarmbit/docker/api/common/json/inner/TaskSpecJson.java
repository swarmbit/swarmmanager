package co.uk.swarmbit.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import co.uk.swarmbit.docker.api.common.annotation.DockerRemoteApiMinVersion;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskSpecJson {

    @JsonProperty("ContainerSpec")
    private ContainerSpecJson containerSpec;

    @JsonProperty("Resources")
    private ResourceRequirementsJson resources;

    @JsonProperty("RestartPolicy")
    private RestartPolicyJson restartPolicy;

    @JsonProperty("Placement")
    private PlacementJson placement;

    @JsonProperty("Networks")
    private NetworkAttachmentConfigJson[] networks;

    @JsonProperty("LogDriver")
    private DriverJson logDriver;

    @DockerRemoteApiMinVersion("v1.25")
    @JsonProperty("ForceUpdate")
    private Long forceUpdate;

    public ContainerSpecJson getContainerSpec() {
        return containerSpec;
    }

    public void setContainerSpec(ContainerSpecJson containerSpec) {
        this.containerSpec = containerSpec;
    }

    public ResourceRequirementsJson getResources() {
        return resources;
    }

    public void setResources(ResourceRequirementsJson resources) {
        this.resources = resources;
    }

    public RestartPolicyJson getRestartPolicy() {
        return restartPolicy;
    }

    public void setRestartPolicy(RestartPolicyJson restartPolicy) {
        this.restartPolicy = restartPolicy;
    }

    public PlacementJson getPlacement() {
        return placement;
    }

    public void setPlacement(PlacementJson placement) {
        this.placement = placement;
    }

    public NetworkAttachmentConfigJson[] getNetworks() {
        return networks;
    }

    public void setNetworks(NetworkAttachmentConfigJson[] networks) {
        this.networks = networks;
    }

    public DriverJson getLogDriver() {
        return logDriver;
    }

    public void setLogDriver(DriverJson logDriver) {
        this.logDriver = logDriver;
    }

    public Long getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Long forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TaskSpecJson{");
        sb.append("containerSpec=").append(containerSpec);
        sb.append(", resources=").append(resources);
        sb.append(", restartPolicy=").append(restartPolicy);
        sb.append(", placement=").append(placement);
        sb.append(", networks=").append(Arrays.toString(networks));
        sb.append(", logDriver=").append(logDriver);
        sb.append(", forceUpdate=").append(forceUpdate);
        sb.append('}');
        return sb.toString();
    }
}
