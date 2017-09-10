package com.swarmmanager.docker.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogLine {

    private String serviceId;

    private String nodeId;

    private String taskId;

    private int replica;

    private String message;

    private long timestamp;

    public LogLine(String serviceId, String nodeId, String taskId, int replica, String message, long timestamp) {
        this.serviceId = serviceId;
        this.nodeId = nodeId;
        this.taskId = taskId;
        this.replica = replica;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getServiceId() {
        return serviceId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getTaskId() {
        return taskId;
    }

    public int getReplica() {
        return replica;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
