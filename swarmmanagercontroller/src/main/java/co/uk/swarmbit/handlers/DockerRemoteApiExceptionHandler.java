package co.uk.swarmbit.handlers;

import co.uk.swarmbit.docker.api.common.client.jaxrs.exception.DockerRemoteApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DockerRemoteApiExceptionHandler {

    @ExceptionHandler(DockerRemoteApiException.class)
    public ResponseEntity<Object> handleDockerRemoteApiException(DockerRemoteApiException exception) {
        System.out.println(exception.getMessage());
        return new ResponseEntity<>(getDesc(exception.getMessage()), HttpStatus.EXPECTATION_FAILED);
    }

    private String getDesc(String message) {
        String descStart = "desc = ";
        if (StringUtils.isNotEmpty(message)) {
            int start = message.indexOf(descStart);
            if (start > -1) {
                return StringUtils.capitalize(message.substring(start + descStart.length()));
            }
        }
        return message;
    }
}
