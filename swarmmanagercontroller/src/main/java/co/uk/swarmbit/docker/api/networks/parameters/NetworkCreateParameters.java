package co.uk.swarmbit.docker.api.networks.parameters;

import co.uk.swarmbit.docker.api.common.json.NetworkJson;
import co.uk.swarmbit.docker.api.common.parameters.RequestBodyParameter;

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
