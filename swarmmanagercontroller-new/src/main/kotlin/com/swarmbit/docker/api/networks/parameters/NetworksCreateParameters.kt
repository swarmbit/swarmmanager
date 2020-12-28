package com.swarmbit.docker.api.networks.parameters

import com.swarmbit.docker.api.common.parameters.RequestBodyParameter
import com.swarmbit.docker.api.networks.model.Network

class NetworksCreateParameters : RequestBodyParameter {

    private var network: Network = Network()

    fun setNetwork(network: Network): NetworksCreateParameters {
        this.network = network
        return this
    }

    override val requestBody: Any
        get() = network

}