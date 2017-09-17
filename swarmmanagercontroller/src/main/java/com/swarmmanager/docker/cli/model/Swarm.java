package com.swarmmanager.docker.cli.model;

import java.util.List;

public class Swarm {

    private long createdAt;

    private long updatedAt;

    private Boolean autolock;

    private Long certExpiry;

    private Long dispatcherHeartBeat;

    private List<ExternalCA> externalCAs;

    private Long maxSnapshots;

    private Long snapshotInterval;

    private Long taskHistoryLimit;

    private String workerToken;

    private String managerToken;

    private boolean rotateInProgress;

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Boolean getAutolock() {
        return autolock;
    }

    public void setAutolock(Boolean autolock) {
        this.autolock = autolock;
    }

    public Long getCertExpiry() {
        return certExpiry;
    }

    public void setCertExpiry(Long certExpiry) {
        this.certExpiry = certExpiry;
    }

    public Long getDispatcherHeartBeat() {
        return dispatcherHeartBeat;
    }

    public void setDispatcherHeartBeat(Long dispatcherHeartBeat) {
        this.dispatcherHeartBeat = dispatcherHeartBeat;
    }

    public List<ExternalCA> getExternalCAs() {
        return externalCAs;
    }

    public void setExternalCAs(List<ExternalCA> externalCAs) {
        this.externalCAs = externalCAs;
    }

    public Long getMaxSnapshots() {
        return maxSnapshots;
    }

    public void setMaxSnapshots(Long maxSnapshots) {
        this.maxSnapshots = maxSnapshots;
    }

    public Long getSnapshotInterval() {
        return snapshotInterval;
    }

    public void setSnapshotInterval(Long snapshotInterval) {
        this.snapshotInterval = snapshotInterval;
    }

    public Long getTaskHistoryLimit() {
        return taskHistoryLimit;
    }

    public void setTaskHistoryLimit(Long taskHistoryLimit) {
        this.taskHistoryLimit = taskHistoryLimit;
    }

    public String getWorkerToken() {
        return workerToken;
    }

    public void setWorkerToken(String workerToken) {
        this.workerToken = workerToken;
    }

    public String getManagerToken() {
        return managerToken;
    }

    public void setManagerToken(String managerToken) {
        this.managerToken = managerToken;
    }

    public boolean isRotateInProgress() {
        return rotateInProgress;
    }

    public void setRotateInProgress(boolean rotateInProgress) {
        this.rotateInProgress = rotateInProgress;
    }
}
