package com.swarmmanager.docker.api.secrets.parameters;

import com.swarmmanager.docker.api.common.QueryParameters;
import com.swarmmanager.docker.api.common.RequestBodyParameter;
import com.swarmmanager.docker.api.common.json.SecretSpecJson;
import com.swarmmanager.rest.QueryParam;

import java.util.ArrayList;
import java.util.List;

public class SecretUpdateParameters implements RequestBodyParameter, QueryParameters {

    private static final String VERSION_NAME = "version";

    private SecretSpecJson secret;

    private QueryParam versionQueryParam;

    public SecretUpdateParameters() {
        secret = new SecretSpecJson();
        versionQueryParam = new QueryParam(VERSION_NAME, 0L);
    }

    public SecretUpdateParameters setSecrect(SecretSpecJson secret) {
        this.secret = secret;
        return this;
    }

    public QueryParam getVersionQueryParam() {
        return versionQueryParam;
    }

    public SecretUpdateParameters setVersionQueryParam(QueryParam versionQueryParam) {
        this.versionQueryParam = versionQueryParam;
        return this;
    }
    @Override
    public List<QueryParam> getQueryParams() {
        List<QueryParam> queryParams = new ArrayList<>();
        queryParams.add(versionQueryParam);
        return queryParams;
    }

    @Override
    public Object getRequestBody() {
        return secret;
    }
}
