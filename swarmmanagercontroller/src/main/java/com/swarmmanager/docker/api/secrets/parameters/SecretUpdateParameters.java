package com.swarmmanager.docker.api.secrets.parameters;

import com.swarmmanager.rest.QueryParam;

public class SecretUpdateParameters {

    private static final String VERSION_NAME = "version";

    private String id;

    private QueryParam versionQueryParam;

    public SecretUpdateParameters() {
        this.versionQueryParam = new QueryParam(VERSION_NAME, 0L);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public QueryParam getVersionQueryParam() {
        return versionQueryParam;
    }

    public void setVersionQueryParam(QueryParam versionQueryParam) {
        this.versionQueryParam = versionQueryParam;
    }
}
