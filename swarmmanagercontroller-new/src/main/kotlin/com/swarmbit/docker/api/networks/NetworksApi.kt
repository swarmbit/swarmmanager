package com.swarmbit.docker.api.networks

import com.swarmbit.docker.api.networks.model.Network
import com.swarmbit.docker.api.networks.model.NetworkCreateResponse
import com.swarmbit.docker.api.networks.parameters.NetworksCreateParameters
import com.swarmbit.docker.api.networks.parameters.NetworksInspectParameters
import com.swarmbit.docker.api.networks.parameters.NetworksListParameters

interface NetworksApi {

    fun listNetworks(swarmId: String, parameters: NetworksListParameters): List<Network>

    fun inspectNetwork(swarmId: String, id: String, parameters: NetworksInspectParameters): Network

    fun createNetwork(swarmId: String, parameters: NetworksCreateParameters): NetworkCreateResponse

    fun deleteNetwork(swarmId: String, id: String)
}