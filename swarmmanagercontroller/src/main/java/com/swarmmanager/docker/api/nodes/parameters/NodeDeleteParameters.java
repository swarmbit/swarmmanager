package com.swarmmanager.docker.api.nodes.parameters;

import com.swarmmanager.rest.QueryParam;

public class NodeDeleteParameters {

    private static final String FORCE_NAME = "force";

    private String id;

    private QueryParam forceQueryParam;

    public NodeDeleteParameters() {
        forceQueryParam = new QueryParam(FORCE_NAME, false);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QueryParam getForceQueryParam() {
        return forceQueryParam;
    }

    public void setForceQueryParam(boolean forceValue) {
        this.forceQueryParam = new QueryParam(FORCE_NAME, forceValue);
    }
}
