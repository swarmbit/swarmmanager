package com.swarmmanager.docker.api.swarm;

import com.swarmmanager.docker.api.swarm.parameters.SwarmUpdateParameters;
import com.swarmmanager.docker.api.common.client.DockerWebClient;
import com.swarmmanager.docker.api.common.json.SwarmJson;
import com.swarmmanager.rest.RestExecutorFactory;
import com.swarmmanager.rest.RestMethod;
import com.swarmmanager.rest.RestParameters;
import com.swarmmanager.rest.RestResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SwarmApiImpl implements SwarmApi {

    private static final String SWARM_PATH = "/swarm";

    private static final String UPDATE_PATH = "/update";

    @Autowired
    private DockerWebClient dockerWebClient;

    @Override
    public SwarmJson inspectSwarm() {
        RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                .setPath(SWARM_PATH);
        return RestExecutorFactory.createRestExecutor(RestMethod.GET).execute(restParameters, new RestResponseType<SwarmJson>() {});
    }

    @Override
    public void updateSwarm(SwarmUpdateParameters parameters) {
        if (parameters != null) {
            RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                    .setPath(SWARM_PATH + UPDATE_PATH)
                    .addQueryParam(parameters.getVersionQueryParam())
                    .addQueryParam(parameters.getRotateManagerTokenQueryParam())
                    .addQueryParam(parameters.getRotateWorkerTokenQueryParam())
                    .addQueryParam(parameters.getRotateManagerUnlockKeyQueryParam())
                    .setRequestBody(parameters.getSpec());
            RestExecutorFactory.createRestExecutor(RestMethod.POST).execute(restParameters, new RestResponseType<Void>() {});
        }
    }
}
