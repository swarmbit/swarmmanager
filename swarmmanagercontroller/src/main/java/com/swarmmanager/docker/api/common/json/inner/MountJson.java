package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class MountJson implements DockerRemoteApiJson {

    @JsonProperty("Type")
    private String type;

    @JsonProperty("Source")
    private String source;

    @JsonProperty("Target")
    private String target;

    @JsonProperty("ReadOnly")
    private boolean readOnly;

    @JsonProperty("BindOptions")
    private BindOptionsJson bindOptions;

    @JsonProperty("VolumeOptions")
    private VolumeOptionsJson volumeOptions;

    @JsonProperty("TmpfsOptions")
    private TmpfsOptionsJson tmpfsOptions;

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

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MountJson{");
        sb.append("type=").append(type);
        sb.append(", source='").append(source).append('\'');
        sb.append(", target='").append(target).append('\'');
        sb.append(", readOnly=").append(readOnly);
        sb.append(", bindOptions=").append(bindOptions);
        sb.append(", volumeOptions=").append(volumeOptions);
        sb.append(", tmpfsOptions=").append(tmpfsOptions);
        sb.append('}');
        return sb.toString();
    }
}
