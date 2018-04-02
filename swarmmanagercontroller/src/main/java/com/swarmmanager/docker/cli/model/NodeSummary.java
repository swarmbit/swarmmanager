package com.swarmmanager.docker.cli.model;

public class NodeSummary {

    private String id;

    private String hostname;

    private String status;

    private String availability;

    private Boolean isManager;

    private Boolean isLeader;

    private String managerReachability;

    private Long numberOfRunningTasks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Boolean isLeader() {
        return isLeader;
    }

    public void setLeader(Boolean leader) {
        isLeader = leader;
    }

    public Boolean isManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    public String getManagerReachability() {
        return managerReachability;
    }

    public void setManagerReachability(String managerReachability) {
        this.managerReachability = managerReachability;
    }

    public Long getNumberOfRunningTasks() {
        return numberOfRunningTasks;
    }

    public void setNumberOfRunningTasks(Long numberOfRunningTasks) {
        this.numberOfRunningTasks = numberOfRunningTasks;
    }
}
