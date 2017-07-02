package com.swarmmanager.docker.api.networks.parameters;

import com.swarmmanager.docker.api.common.parameters.RequestBodyParameter;
import com.swarmmanager.docker.api.common.json.NetworkJson;

public class NetworkCreateParameters implements RequestBodyParameter {

    private NetworkJson network;

    public NetworkCreateParameters() {
        network = new NetworkJson();
    }

    public NetworkCreateParameters setNetwork(NetworkJson network) {
        this.network = network;
        return this;
    }
    @Override
    public Object getRequestBody() {
        return network;
    }
}
