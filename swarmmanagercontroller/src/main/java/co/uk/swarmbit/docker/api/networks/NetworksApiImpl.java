package co.uk.swarmbit.docker.api.networks;

import co.uk.swarmbit.docker.api.common.AbstractApiImpl;
import co.uk.swarmbit.docker.api.common.json.NetworkJson;
import co.uk.swarmbit.docker.api.networks.parameters.NetworkListParameters;
import co.uk.swarmbit.docker.api.common.json.NetworkCreateResponseJson;
import co.uk.swarmbit.docker.api.networks.parameters.NetworkCreateParameters;
import co.uk.swarmbit.rest.RestResponseType;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class NetworksApiImpl extends AbstractApiImpl implements NetworksApi {

    private static final String NETWORKS_PATH = "/networks";

    private static final String CREATE_PATH = "/create";

    @Override
    public List<NetworkJson> listNetworks(String swarmId, NetworkListParameters parameters) {
        return listObjects(NETWORKS_PATH, swarmId, new RestResponseType<List<NetworkJson>>(){}, parameters);
    }

    @Override
    public NetworkJson inspectNetwork(String swarmId, String id) {
        return inspectObject(NETWORKS_PATH + "/" + id, swarmId, new RestResponseType<NetworkJson>(){});
    }

    @Override
    public NetworkCreateResponseJson createNetwork(String swarmId, NetworkCreateParameters parameters) {
        return createObject(NETWORKS_PATH + CREATE_PATH, swarmId, new RestResponseType<NetworkCreateResponseJson>(){}, parameters);
    }

    @Override
    public void deleteNetwork(String swarmId, String id) {
        deleteObject(NETWORKS_PATH + "/" + id, swarmId, new RestResponseType<Void>(){});
    }
}
