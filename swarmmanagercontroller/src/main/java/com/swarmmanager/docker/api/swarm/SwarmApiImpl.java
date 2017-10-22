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
    public SwarmJson inspectSwarm(String swarmId) {
        return inspectObject(SWARM_PATH, swarmId, new RestResponseType<SwarmJson>() {});
    }

    @Override
    public void updateSwarm(String swarmId, SwarmUpdateParameters parameters) {
        updateObject(SWARM_PATH + UPDATE_PATH, swarmId, new RestResponseType<Void>() {}, parameters, parameters);
    }

    @Override
    public UnlockKeyJson unlock(String swarmId) {
        RestParameters restParameters = new RestParameters(dockerWebClient.getBaseResource(swarmId))
                .setPath(SWARM_PATH + UNLOCK_PATH);
        return RestExecutorFactory.createRestExecutor(RestMethod.POST).execute(restParameters, new RestResponseType<UnlockKeyJson>() {});
    }
}
