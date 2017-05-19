package com.swarmmanager.docker.command.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class LogLine {

    private int replicaNumber;

    private String taskId;

    private String line;

    public LogLine(int replicaNumber, String taskId, String line) {
        this.replicaNumber = replicaNumber;
        this.taskId = taskId;
        this.line = line;
    }

    public int getReplicaNumber() {
        return replicaNumber;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getLine() {
        return line;
    }


}
