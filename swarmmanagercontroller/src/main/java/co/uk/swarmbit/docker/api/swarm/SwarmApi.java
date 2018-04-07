package co.uk.swarmbit.docker.api.swarm;

import co.uk.swarmbit.docker.api.common.json.UnlockKeyJson;
import co.uk.swarmbit.docker.api.swarm.parameters.SwarmUpdateParameters;
import co.uk.swarmbit.docker.api.common.json.SwarmJson;

public interface SwarmApi {

    SwarmJson inspectSwarm(String swarmId);

    void updateSwarm(String swarmId, SwarmUpdateParameters parameters);

    UnlockKeyJson unlock(String swarmId);
}
