package co.uk.swarmbit.docker.api.secrets.parameters;

import co.uk.swarmbit.docker.api.common.parameters.QueryParameters;
import co.uk.swarmbit.docker.api.common.parameters.RequestBodyParameter;
import co.uk.swarmbit.docker.api.common.json.SecretSpecJson;
import co.uk.swarmbit.rest.QueryParam;

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

    public SecretUpdateParameters setVersionQueryParam(long versionValue) {
        this.versionQueryParam = new QueryParam(VERSION_NAME, versionValue);
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
