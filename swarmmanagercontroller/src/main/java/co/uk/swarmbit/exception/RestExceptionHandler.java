package co.uk.swarmbit.exception;

import co.uk.swarmbit.docker.api.common.client.jaxrs.exception.DockerRemoteApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ServerException> handleException(MethodArgumentNotValidException exception) {
        ServerException serverException = new ServerException(HttpStatus.BAD_REQUEST.value());
        List<ServerException.Error> errorList = new ArrayList<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError -> {
            ServerException.Error error = new ServerException.Error();
            error.setCode(StringUtils.lowerCase(fieldError.getCode()));
            error.setMessage(fieldError.getDefaultMessage());
            errorList.add(error);
        });
        serverException.addErrors(errorList);
        return new ResponseEntity<>(serverException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ServerException> validationException(ValidationException validationException) {
        ServerException serverException = new ServerException(HttpStatus.BAD_REQUEST.value());
        List<ServerException.Error> errorList = new ArrayList<>();
        ServerException.Error error = new ServerException.Error();
        error.setCode(validationException.getCode());
        error.setMessage(validationException.getMessage());
        errorList.add(error);
        serverException.addErrors(errorList);
        return new ResponseEntity<>(serverException, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(DockerRemoteApiException.class)
    public ResponseEntity<ServerException> handleDockerRemoteApiException(DockerRemoteApiException exception) {
        ServerException serverException = new ServerException(exception.getStatus());
        ServerException.Error error = new ServerException.Error();
        error.setCode("docker");
        error.setMessage(getDesc(exception.getMessage()));
        serverException.addErrors(Collections.singletonList(error));
        return new ResponseEntity<>(serverException, HttpStatus.valueOf(exception.getStatus()));
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
