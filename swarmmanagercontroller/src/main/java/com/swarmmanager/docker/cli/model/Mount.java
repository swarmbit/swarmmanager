package com.swarmmanager.docker.cli.model;

public class Mount {

    public static final String VOLUME_TYPE = "volume";

    public static final String BIND_TYPE = "bind";

    public static final String TMPFS_TYPE = "tmpfs";

    private String type;

    private String source;

    private String destination;

    private Boolean readOnly;

    private String consistency;

    private BindMountOptions bindOptions;

    private VolumeMountOptions volumeOptions;

    private TmpfsMountOptions tmpfsOptions;

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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Boolean getReadOnly() {
        return readOnly;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getConsistency() {
        return consistency;
    }

    public void setConsistency(String consistency) {
        this.consistency = consistency;
    }

    public BindMountOptions getBindOptions() {
        return bindOptions;
    }

    public void setBindOptions(BindMountOptions bindOptions) {
        this.bindOptions = bindOptions;
    }

    public VolumeMountOptions getVolumeOptions() {
        return volumeOptions;
    }

    public void setVolumeOptions(VolumeMountOptions volumeOptions) {
        this.volumeOptions = volumeOptions;
    }

    public TmpfsMountOptions getTmpfsOptions() {
        return tmpfsOptions;
    }

    public void setTmpfsMountOptions(TmpfsMountOptions tmpfsOptions) {
        this.tmpfsOptions = tmpfsOptions;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Mount{");
        sb.append("type='").append(type).append('\'');
        sb.append(", source='").append(source).append('\'');
        sb.append(", destination='").append(destination).append('\'');
        sb.append(", readOnly=").append(readOnly);
        sb.append(", consistency='").append(consistency).append('\'');
        sb.append(", bindMountOptions=").append(bindOptions);
        sb.append(", volumeMountOptions=").append(volumeOptions);
        sb.append(", tmpfsMountOptions=").append(tmpfsOptions);
        sb.append('}');
        return sb.toString();
    }
}
