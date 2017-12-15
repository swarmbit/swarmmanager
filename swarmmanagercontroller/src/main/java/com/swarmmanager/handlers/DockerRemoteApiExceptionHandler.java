package com.swarmmanager.handlers;

import com.swarmmanager.docker.api.common.client.jaxrs.exception.DockerRemoteApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DockerRemoteApiExceptionHandler {

    @ExceptionHandler(DockerRemoteApiException.class)
    public ResponseEntity<Object> handleDockerRemoteApiException(DockerRemoteApiException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }
}
