package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MountJson {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Source")
    private String source;

    @JsonProperty("Target")
    private String target;

    @DockerRemoteApiMinVersion(version = "v1.28")
    @JsonProperty("ReadOnly")
    private Boolean readOnly;

    @JsonProperty("BindOptions")
    private BindOptionsJson bindOptions;

    @JsonProperty("VolumeOptions")
    private VolumeOptionsJson volumeOptions;

    @JsonProperty("TmpfsOptions")
    private TmpfsOptionsJson tmpfsOptions;

    @DockerRemoteApiMinVersion(version = "v1.29")
    @JsonProperty("Consistency")
    private String consistency;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public BindOptionsJson getBindOptions() {
        return bindOptions;
    }

    public void setBindOptions(BindOptionsJson bindOptions) {
        this.bindOptions = bindOptions;
    }

    public VolumeOptionsJson getVolumeOptions() {
        return volumeOptions;
    }

    public void setVolumeOptions(VolumeOptionsJson volumeOptions) {
        this.volumeOptions = volumeOptions;
    }

    public TmpfsOptionsJson getTmpfsOptions() {
        return tmpfsOptions;
    }

    public void setTmpfsOptions(TmpfsOptionsJson tmpfsOptions) {
        this.tmpfsOptions = tmpfsOptions;
    }

    public String getConsistency() {
        return consistency;
    }

    public void setConsistency(String consistency) {
        this.consistency = consistency;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MountJson{");
        sb.append("type='").append(type).append('\'');
        sb.append(", source='").append(source).append('\'');
        sb.append(", target='").append(target).append('\'');
        sb.append(", readOnly=").append(readOnly);
        sb.append(", bindOptions=").append(bindOptions);
        sb.append(", volumeOptions=").append(volumeOptions);
        sb.append(", tmpfsOptions=").append(tmpfsOptions);
        sb.append(", consistency='").append(consistency).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
