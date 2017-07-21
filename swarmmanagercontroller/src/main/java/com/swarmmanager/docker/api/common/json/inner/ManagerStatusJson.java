package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagerStatusJson {

    @JsonProperty("Leader")
    private boolean leader;

    @JsonProperty("Reachability")
    private String reachability;

    @JsonProperty("Addr")
    private String addr;

    public boolean isLeader() {
        return leader;
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }

    public String getReachability() {
        return reachability;
    }

    public void setReachability(String reachability) {
        this.reachability = reachability;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
