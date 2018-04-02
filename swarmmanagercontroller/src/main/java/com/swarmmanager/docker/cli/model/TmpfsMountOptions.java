package com.swarmmanager.docker.cli.model;

public class TmpfsMountOptions {

    private Long size;

    private Integer mode;

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
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
        sb.append("size=").append(size);
        sb.append(", mode=").append(mode);
        sb.append('}');
        return sb.toString();
    }
}
