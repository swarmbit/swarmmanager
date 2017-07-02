package com.swarmmanager.docker.api.swarm.parameters;

import com.swarmmanager.docker.api.common.parameters.QueryParameters;
import com.swarmmanager.docker.api.common.parameters.RequestBodyParameter;
import com.swarmmanager.docker.api.common.json.SwarmSpecJson;
import com.swarmmanager.rest.QueryParam;

import java.util.ArrayList;
import java.util.List;

public class SwarmUpdateParameters implements RequestBodyParameter, QueryParameters {

    private static final String VERSION_NAME = "version";

    private static final String ROTATE_WORKER_TOKEN_NAME = "rotateWorkerToken";

    private static final String ROTATE_MANAGER_TOKEN_NAME = "rotateManagerToken";

    private static final String ROTATE_MANAGER_UNLOCK_KEY_NAME = "rotateManagerUnlockKey";

    private QueryParam versionQueryParam;

    private SwarmSpecJson spec;

    private QueryParam rotateWorkerTokenQueryParam;

    private QueryParam rotateManagerTokenQueryParam;

    private QueryParam rotateManagerUnlockKeyQueryParam;

    public SwarmUpdateParameters() {
        spec = new SwarmSpecJson();
        versionQueryParam = new QueryParam(VERSION_NAME, 0L);
        rotateWorkerTokenQueryParam = new QueryParam(ROTATE_WORKER_TOKEN_NAME,false);
        rotateManagerTokenQueryParam = new QueryParam(ROTATE_MANAGER_TOKEN_NAME, false);
        rotateManagerUnlockKeyQueryParam = new QueryParam(ROTATE_MANAGER_UNLOCK_KEY_NAME, false);
    }

    public QueryParam getVersionQueryParam() {
        return versionQueryParam;
    }

    public SwarmUpdateParameters setVersionQueryParam(long versionValue) {
        this.versionQueryParam = new QueryParam(VERSION_NAME, versionValue);
        return this;
    }

    public SwarmUpdateParameters setSpec(SwarmSpecJson spec) {
        this.spec = spec;
        return this;
    }

    public SwarmUpdateParameters setRotateWorkerTokenQueryParam(boolean rotateWorkerTokenValue) {
        this.rotateWorkerTokenQueryParam = new QueryParam(ROTATE_WORKER_TOKEN_NAME, rotateWorkerTokenValue);
        return this;
    }

    public SwarmUpdateParameters setRotateManagerTokenQueryParam(boolean rotateManagerTokenValue) {
        this.rotateManagerTokenQueryParam = new QueryParam(ROTATE_MANAGER_TOKEN_NAME, rotateManagerTokenValue);
        return this;
    }

    public SwarmUpdateParameters setRotateManagerUnlockKeyQueryParam(boolean rotateManagerUnlockKeyValue) {
        this.rotateManagerUnlockKeyQueryParam = new QueryParam(ROTATE_MANAGER_UNLOCK_KEY_NAME, rotateManagerUnlockKeyValue);
        return this;
    }

    @Override
    public Object getRequestBody() {
        return spec;
    }

    @Override
    public List<QueryParam> getQueryParams() {
        List<QueryParam> queryParams = new ArrayList<>();
        queryParams.add(versionQueryParam);
        queryParams.add(rotateWorkerTokenQueryParam);
        queryParams.add(rotateManagerTokenQueryParam);
        queryParams.add(rotateManagerUnlockKeyQueryParam);
        return queryParams;
    }
}
