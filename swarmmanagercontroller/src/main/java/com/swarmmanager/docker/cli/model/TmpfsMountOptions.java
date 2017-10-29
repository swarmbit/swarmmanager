package com.swarmmanager.docker.cli.model;

public class TmpfsMountOptions {

    private Long sizeBytes;

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
        final StringBuilder sb = new StringBuilder("TmpfsMountOptions{");
        sb.append("sizeBytes=").append(sizeBytes);
        sb.append(", mode=").append(mode);
        sb.append('}');
        return sb.toString();
    }
}
