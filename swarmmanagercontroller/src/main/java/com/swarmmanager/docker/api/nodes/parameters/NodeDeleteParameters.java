package com.swarmmanager.docker.api.nodes.parameters;

import com.swarmmanager.docker.api.common.parameters.QueryParameters;
import com.swarmmanager.rest.QueryParam;

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
