package com.swarmmanager.docker.api.common.client.jaxrs.exception;

public class DockerRemoteApiException extends RuntimeException {

    private String message;

    private int status;

    public DockerRemoteApiException(String message, int status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
