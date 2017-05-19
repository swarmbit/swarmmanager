package com.swarmmanager.docker.api.swarm.parameters;

import com.swarmmanager.docker.api.common.json.SwarmSpecJson;
import com.swarmmanager.rest.QueryParam;

public class SwarmUpdateParameters {

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

    public void setVersionQueryParam(long versionValue) {
        this.versionQueryParam = new QueryParam(VERSION_NAME, versionValue);
    }

    public SwarmSpecJson getSpec() {
        return spec;
    }

    public void setSpec(SwarmSpecJson spec) {
        this.spec = spec;
    }

    public QueryParam getRotateWorkerTokenQueryParam() {
        return rotateWorkerTokenQueryParam;
    }

    public void setRotateWorkerTokenQueryParam(boolean rotateWorkerTokenValue) {
        this.rotateWorkerTokenQueryParam = new QueryParam(ROTATE_WORKER_TOKEN_NAME, rotateWorkerTokenValue);
    }

    public QueryParam getRotateManagerTokenQueryParam() {
        return rotateManagerTokenQueryParam;
    }

    public void setRotateManagerTokenQueryParam(boolean rotateManagerTokenValue) {
        this.rotateManagerTokenQueryParam = new QueryParam(ROTATE_MANAGER_TOKEN_NAME, rotateManagerTokenValue);
    }

    public QueryParam getRotateManagerUnlockKeyQueryParam() {
        return rotateManagerUnlockKeyQueryParam;
    }

    public void setRotateManagerUnlockKeyQueryParam(boolean rotateManagerUnlockKeyValue) {
        this.rotateManagerUnlockKeyQueryParam = new QueryParam(ROTATE_MANAGER_UNLOCK_KEY_NAME, rotateManagerUnlockKeyValue);
    }
}
