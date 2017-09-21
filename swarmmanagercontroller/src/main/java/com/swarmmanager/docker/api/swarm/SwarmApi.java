package com.swarmmanager.docker.api.swarm;

import com.swarmmanager.docker.api.common.json.UnlockKeyJson;
import com.swarmmanager.docker.api.swarm.parameters.SwarmUpdateParameters;
import com.swarmmanager.docker.api.common.json.SwarmJson;

public interface SwarmApi {

    SwarmJson inspectSwarm();

    void updateSwarm(SwarmUpdateParameters parameters);

    UnlockKeyJson unlock();
}
