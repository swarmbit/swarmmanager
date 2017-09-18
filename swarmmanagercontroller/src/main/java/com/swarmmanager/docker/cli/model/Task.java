package com.swarmmanager.docker.cli.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Duration;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Task {

    private String id;

    private int replica;

    private String serviceId;

    private String serviceName;

    private String image;

    private String nodeId;

    private String nodeHostname;

    private String desiredState;

    private String state;

    private Duration lastStateChange;

    private String errorMessage;

    private List<Port> ports;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getReplica() {
        return replica;
    }

    public void setReplica(int replica) {
        this.replica = replica;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeHostname() {
        return nodeHostname;
    }

    public void setNodeHostname(String nodeHostname) {
        this.nodeHostname = nodeHostname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDesiredState() {
        return desiredState;
    }

    public void setDesiredState(String desiredState) {
        this.desiredState = desiredState;
    }

    public Duration getLastStateChange() {
        return lastStateChange;
    }

    public void setLastStateChange(Duration lastStateChange) {
        this.lastStateChange = lastStateChange;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Port> getPorts() {
        return ports;
    }

    public void setPorts(List<Port> ports) {
        this.ports = ports;
    }

}
