package com.swarmmanager.docker.api.networks;

import com.swarmmanager.docker.api.common.json.NetworkCreateResponseJson;
import com.swarmmanager.docker.api.common.json.NetworkJson;
import com.swarmmanager.docker.api.networks.parameters.NetworkCreateParameters;
import com.swarmmanager.docker.api.networks.parameters.NetworkListParameters;

import java.util.List;

public interface NetworksApi {

    List<NetworkJson> listNetworks(String swarmId, NetworkListParameters parameters);

    NetworkJson inspectNetwork(String swarmId, String id);

    NetworkCreateResponseJson createNetwork(String swarmId, NetworkCreateParameters parameters);

    void deleteNetwork(String swarmId, String id);

}
