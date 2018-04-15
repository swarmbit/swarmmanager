package co.uk.swarmbit.exception;

import java.util.ArrayList;
import java.util.List;

public class ServerException {

    private int status;

    private List<Error> errors;

    public ServerException(int status) {
        this.status = status;
        this.errors = new ArrayList<>();
    }

    public int getStatus() {
        return status;
    }

    public void addErrors(List<Error> errors) {
        this.errors.addAll(errors);
    }

    public List<Error> getErrors() {
        return errors;
    }

    public static class Error {
        private String code;
        private String message;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
