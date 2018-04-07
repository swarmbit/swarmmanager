package co.uk.swarmbit.docker.api.services.parameters;

import co.uk.swarmbit.rest.QueryParam;
import co.uk.swarmbit.docker.api.common.parameters.QueryParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceLogsParameters implements QueryParameters {

    private static final String STDOUT_NAME = "stdout";

    private static final String STDERR_NAME = "stderr";

    private static final String SINCE_NAME = "since";

    private static final String TIMESTAMPS_NAME = "timestamps";

    private static final String TAIL_NAME = "tail";

    private static final String DETAILS_NAME = "details";

    private QueryParam stdoutQueryParam;

    private QueryParam stderrQueryParam;

    private QueryParam timestampsQueryParam;

    private QueryParam detailsQueryParam;

    private Optional<QueryParam> sinceQueryParam;

    private Optional<QueryParam> tailQueryParam;

    public ServiceLogsParameters() {
        stdoutQueryParam = new QueryParam(STDOUT_NAME, false);
        stderrQueryParam = new QueryParam(STDERR_NAME, false);
        timestampsQueryParam = new QueryParam(TIMESTAMPS_NAME, false);
        detailsQueryParam = new QueryParam(DETAILS_NAME, false);
        sinceQueryParam = Optional.empty();
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

    public QueryParam getDetailsQueryParam() {
        return timestampsQueryParam;
    }

    public ServiceLogsParameters setDetailsQueryParam(boolean details) {
        this.detailsQueryParam = new QueryParam(DETAILS_NAME, details);
        return this;
    }

    public QueryParam getTimestampsQueryParam() {
        return timestampsQueryParam;
    }

    public ServiceLogsParameters setTimestampsQueryParam(boolean timestampValue) {
        this.timestampsQueryParam = new QueryParam(TIMESTAMPS_NAME, timestampValue);
        return this;
    }

    public Optional<QueryParam> getTailQueryParam() {
        return tailQueryParam;
    }

    public ServiceLogsParameters setTailQueryParam(int tailValue) {
        this.tailQueryParam = Optional.of(new QueryParam(TAIL_NAME, tailValue));
        return this;
    }

    public Optional<QueryParam> getSinceQueryParam() {
        return sinceQueryParam;
    }

    public ServiceLogsParameters setSinceQueryParam(long sinceValue) {
        this.sinceQueryParam = Optional.of(new QueryParam(SINCE_NAME, sinceValue));
        return this;
    }

    @Override
    public List<QueryParam> getQueryParams() {
        List<QueryParam> queryParams = new ArrayList<>();
        queryParams.add(stdoutQueryParam);
        queryParams.add(stderrQueryParam);
        queryParams.add(detailsQueryParam);
        queryParams.add(timestampsQueryParam);
        sinceQueryParam.ifPresent(queryParams::add);
        tailQueryParam.ifPresent(queryParams::add);
        return queryParams;
    }
}
