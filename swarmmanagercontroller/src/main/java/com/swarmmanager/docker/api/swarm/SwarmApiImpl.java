package com.swarmmanager.docker.api.swarm;

import com.swarmmanager.docker.api.common.AbstractApiImpl;
import com.swarmmanager.docker.api.common.json.UnlockKeyJson;
import com.swarmmanager.docker.api.swarm.parameters.SwarmUpdateParameters;
import com.swarmmanager.docker.api.common.json.SwarmJson;
import com.swarmmanager.rest.RestExecutorFactory;
import com.swarmmanager.rest.RestMethod;
import com.swarmmanager.rest.RestParameters;
import com.swarmmanager.rest.RestResponseType;
import org.springframework.stereotype.Component;

@Component
public class SwarmApiImpl  extends AbstractApiImpl implements SwarmApi {

    private static final String SWARM_PATH = "/swarm";

    private static final String UNLOCK_PATH = "/unlock";

    private static final String UPDATE_PATH = "/update";

    @Override
    public SwarmJson inspectSwarm() {
        return inspectObject(SWARM_PATH,  new RestResponseType<SwarmJson>() {});
    }

    @Override
    public void updateSwarm(SwarmUpdateParameters parameters) {
        updateObject(SWARM_PATH + UPDATE_PATH, new RestResponseType<Void>() {}, parameters, parameters);
    }

    @Override
    public UnlockKeyJson unlock() {
        RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource())
                .setPath(SWARM_PATH + UNLOCK_PATH);
        return RestExecutorFactory.createRestExecutor(RestMethod.POST).execute(restParameters, new RestResponseType<UnlockKeyJson>() {});
    }
}
