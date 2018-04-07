package co.uk.swarmbit.docker.api.swarm;

import co.uk.swarmbit.docker.api.common.AbstractApiImpl;
import co.uk.swarmbit.docker.api.swarm.parameters.SwarmUpdateParameters;
import co.uk.swarmbit.docker.api.common.json.UnlockKeyJson;
import co.uk.swarmbit.docker.api.common.json.SwarmJson;
import co.uk.swarmbit.rest.RestExecutorFactory;
import co.uk.swarmbit.rest.RestMethod;
import co.uk.swarmbit.rest.RestParameters;
import co.uk.swarmbit.rest.RestResponseType;
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
