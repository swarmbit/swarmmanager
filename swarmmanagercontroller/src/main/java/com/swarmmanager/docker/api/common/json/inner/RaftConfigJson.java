package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RaftConfigJson {

    @JsonProperty("SnapshotInterval")
    private long snapshotInterval;

    @JsonProperty("KeepOldSnapshots")
    private long keepOldSnapshots;

    @JsonProperty("LogEntriesForSlowFollowers")
    private long logEntriesForSlowFollowers;

    @JsonProperty("ElectionTick")
    private int electionTick;

    @JsonProperty("HeartbeatTick")
    private int heartbeatTick;

    public long getSnapshotInterval() {
        return snapshotInterval;
    }

    public void setSnapshotInterval(long snapshotInterval) {
        this.snapshotInterval = snapshotInterval;
    }

    public long getKeepOldSnapshots() {
        return keepOldSnapshots;
    }

    public void setKeepOldSnapshots(long keepOldSnapshots) {
        this.keepOldSnapshots = keepOldSnapshots;
    }

    public long getLogEntriesForSlowFollowers() {
        return logEntriesForSlowFollowers;
    }

    public void setLogEntriesForSlowFollowers(long logEntriesForSlowFollowers) {
        this.logEntriesForSlowFollowers = logEntriesForSlowFollowers;
    }

    public int getElectionTick() {
        return electionTick;
    }

    public void setElectionTick(int electionTick) {
        this.electionTick = electionTick;
    }

    public int getHeartbeatTick() {
        return heartbeatTick;
    }

    public void setHeartbeatTick(int heartbeatTick) {
        this.heartbeatTick = heartbeatTick;
    }
}
