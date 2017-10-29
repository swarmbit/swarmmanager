package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RaftConfigJson {

    @JsonProperty("SnapshotInterval")
    private Long snapshotInterval;

    @JsonProperty("KeepOldSnapshots")
    private Long keepOldSnapshots;

    @JsonProperty("LogEntriesForSlowFollowers")
    private Long logEntriesForSlowFollowers;

    @JsonProperty("ElectionTick")
    private Integer electionTick;

    @JsonProperty("HeartbeatTick")
    private Integer heartbeatTick;

    public Long getSnapshotInterval() {
        return snapshotInterval;
    }

    public void setSnapshotInterval(Long snapshotInterval) {
        this.snapshotInterval = snapshotInterval;
    }

    public Long getKeepOldSnapshots() {
        return keepOldSnapshots;
    }

    public void setKeepOldSnapshots(Long keepOldSnapshots) {
        this.keepOldSnapshots = keepOldSnapshots;
    }

    public Long getLogEntriesForSlowFollowers() {
        return logEntriesForSlowFollowers;
    }

    public void setLogEntriesForSlowFollowers(Long logEntriesForSlowFollowers) {
        this.logEntriesForSlowFollowers = logEntriesForSlowFollowers;
    }

    public Integer getElectionTick() {
        return electionTick;
    }

    public void setElectionTick(Integer electionTick) {
        this.electionTick = electionTick;
    }

    public Integer getHeartbeatTick() {
        return heartbeatTick;
    }

    public void setHeartbeatTick(Integer heartbeatTick) {
        this.heartbeatTick = heartbeatTick;
    }
}
