package com.swarmmanager.docker.api.swarm;

import com.swarmmanager.docker.api.common.AbstractApiImpl;
import com.swarmmanager.docker.api.swarm.parameters.SwarmUpdateParameters;
import com.swarmmanager.docker.api.common.json.SwarmJson;
import com.swarmmanager.rest.RestResponseType;
import org.springframework.stereotype.Component;

@Component
public class SwarmApiImpl  extends AbstractApiImpl implements SwarmApi {

    private static final String SWARM_PATH = "/swarm";

    private static final String UPDATE_PATH = "/update";

    @Override
    public SwarmJson inspectSwarm() {
        return inspectObject(SWARM_PATH,  new RestResponseType<SwarmJson>() {});
    }

    @Override
    public void updateSwarm(SwarmUpdateParameters parameters) {
        updateObject(SWARM_PATH + "/" + UPDATE_PATH, new RestResponseType<Void>() {}, parameters, parameters);
    }
}
