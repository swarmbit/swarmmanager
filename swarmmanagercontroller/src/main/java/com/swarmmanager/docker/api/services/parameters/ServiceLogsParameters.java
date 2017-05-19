package com.swarmmanager.docker.api.services.parameters;

import com.swarmmanager.rest.QueryParam;

import java.util.Optional;

public class ServiceLogsParameters {

    private static final String STDOUT_NAME = "stdout";

    private static final String STDERR_NAME = "stderr";

    private static final String SINCE_NAME = "since";

    private static final String TIMESTAMP_NAME = "timestamp";

    private static final String TAIL_NAME = "tail";

    private String id;

    private QueryParam stdoutQueryParam;

    private QueryParam stderrQueryParam;

    private QueryParam sinceQueryParam;

    private QueryParam timestampQueryParam;

    private Optional<QueryParam> tailQueryParam;

    public ServiceLogsParameters() {
        stdoutQueryParam = new QueryParam(STDOUT_NAME, false);
        stderrQueryParam = new QueryParam(STDERR_NAME, false);
        sinceQueryParam = new QueryParam(SINCE_NAME, 0);
        timestampQueryParam = new QueryParam(TIMESTAMP_NAME, false);
        tailQueryParam = Optional.empty();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QueryParam getStdoutQueryParam() {
        return stdoutQueryParam;
    }

    public void setStdoutQueryParam(boolean stdoutValue) {
        this.stdoutQueryParam = new QueryParam(STDOUT_NAME, stdoutValue);
    }

    public QueryParam getStderrQueryParam() {
        return stderrQueryParam;
    }

    public void setStderrQueryParam(boolean stderrValue) {
        this.stderrQueryParam = new QueryParam(STDERR_NAME, stderrValue);
    }

    public QueryParam getSinceQueryParam() {
        return sinceQueryParam;
    }

    public void setSinceQueryParam(long sinceValue) {
        this.sinceQueryParam = new QueryParam(SINCE_NAME, sinceValue);
    }

    public QueryParam getTimestampQueryParam() {
        return timestampQueryParam;
    }

    public void setTimestampQueryParam(boolean timestampValue) {
        this.timestampQueryParam = new QueryParam(TIMESTAMP_NAME, timestampValue);
    }

    public Optional<QueryParam> getTailQueryParam() {
        return tailQueryParam;
    }

    public void setTailQueryParam(int tailValue) {
        this.tailQueryParam = Optional.of(new QueryParam(TAIL_NAME, tailValue));
    }
}
