package co.uk.swarmbit.docker.api.configs.parameters;

import co.uk.swarmbit.docker.api.common.json.ConfigSpecJson;
import co.uk.swarmbit.docker.api.common.parameters.QueryParameters;
import co.uk.swarmbit.rest.QueryParam;
import co.uk.swarmbit.docker.api.common.parameters.RequestBodyParameter;

import java.util.ArrayList;
import java.util.List;

public class ConfigsUpdateParameters implements RequestBodyParameter, QueryParameters {

    private static final String VERSION_NAME = "version";

    private ConfigSpecJson config;

    private QueryParam versionQueryParam;

    public ConfigsUpdateParameters() {
        config = new ConfigSpecJson();
        versionQueryParam = new QueryParam(VERSION_NAME, 0L);
    }

    public ConfigsUpdateParameters setConfig(ConfigSpecJson config) {
        this.config = config;
        return this;
    }

    public QueryParam getVersionQueryParam() {
        return versionQueryParam;
    }

    public ConfigsUpdateParameters setVersionQueryParam(long versionValue) {
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
        return config;
    }
}
