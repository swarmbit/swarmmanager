package co.uk.swarmbit.docker.api.nodes.parameters;

import co.uk.swarmbit.docker.api.common.parameters.QueryParameters;
import co.uk.swarmbit.rest.QueryParam;

import java.util.ArrayList;
import java.util.List;

public class NodeDeleteParameters implements QueryParameters {

    private static final String FORCE_NAME = "force";

    private QueryParam forceQueryParam;

    public NodeDeleteParameters() {
        forceQueryParam = new QueryParam(FORCE_NAME, false);
    }

    public NodeDeleteParameters setForceQueryParam(boolean forceValue) {
        this.forceQueryParam = new QueryParam(FORCE_NAME, forceValue);
        return this;
    }

    @Override
    public List<QueryParam> getQueryParams() {
        List<QueryParam> queryParams = new ArrayList<>();
        queryParams.add(forceQueryParam);
        return queryParams;
    }
}
