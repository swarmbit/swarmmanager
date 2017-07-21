package com.swarmmanager.docker.api.services.parameters;

import com.swarmmanager.docker.api.common.parameters.QueryParameters;
import com.swarmmanager.rest.QueryParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceLogsParameters implements QueryParameters {

    private static final String STDOUT_NAME = "stdout";

    private static final String STDERR_NAME = "stderr";

    private static final String SINCE_NAME = "since";

    private static final String TIMESTAMP_NAME = "timestamp";

    private static final String TAIL_NAME = "tail";

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

    public QueryParam getStdoutQueryParam() {
        return stdoutQueryParam;
    }

    public ServiceLogsParameters setStdoutQueryParam(boolean stdoutValue) {
        this.stdoutQueryParam = new QueryParam(STDOUT_NAME, stdoutValue);
        return this;
    }

    public QueryParam getStderrQueryParam() {
        return stderrQueryParam;
    }

    public ServiceLogsParameters setStderrQueryParam(boolean stderrValue) {
        this.stderrQueryParam = new QueryParam(STDERR_NAME, stderrValue);
        return this;
    }

    public QueryParam getSinceQueryParam() {
        return sinceQueryParam;
    }

    public ServiceLogsParameters setSinceQueryParam(long sinceValue) {
        this.sinceQueryParam = new QueryParam(SINCE_NAME, sinceValue);
        return this;
    }

    public QueryParam getTimestampQueryParam() {
        return timestampQueryParam;
    }

    public ServiceLogsParameters setTimestampQueryParam(boolean timestampValue) {
        this.timestampQueryParam = new QueryParam(TIMESTAMP_NAME, timestampValue);
        return this;
    }

    public Optional<QueryParam> getTailQueryParam() {
        return tailQueryParam;
    }

    public ServiceLogsParameters setTailQueryParam(int tailValue) {
        this.tailQueryParam = Optional.of(new QueryParam(TAIL_NAME, tailValue));
        return this;
    }

    @Override
    public List<QueryParam> getQueryParams() {
        List<QueryParam> queryParams = new ArrayList<>();
        queryParams.add(stdoutQueryParam);
        queryParams.add(stderrQueryParam);
        queryParams.add(sinceQueryParam);
        queryParams.add(timestampQueryParam);
        tailQueryParam.ifPresent(queryParams::add);
        return queryParams;
    }
}
