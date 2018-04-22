package co.uk.swarmbit.docker.api.networks;

import co.uk.swarmbit.docker.api.common.json.NetworkJson;
import co.uk.swarmbit.docker.api.common.json.NetworkCreateResponseJson;
import co.uk.swarmbit.docker.api.networks.parameters.NetworkCreateParameters;
import co.uk.swarmbit.docker.api.networks.parameters.NetworkInspectParameters;
import co.uk.swarmbit.docker.api.networks.parameters.NetworkListParameters;

import java.util.List;

public interface NetworksApi {

    List<NetworkJson> listNetworks(String swarmId, NetworkListParameters parameters);

    NetworkJson inspectNetwork(String swarmId, String id, NetworkInspectParameters parameters);

    NetworkCreateResponseJson createNetwork(String swarmId, NetworkCreateParameters parameters);

    void deleteNetwork(String swarmId, String id);

}
