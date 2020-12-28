package com.swarmbit.docker.api.networks

import com.swarmbit.docker.api.common.AbstractApiImpl
import com.swarmbit.docker.api.common.client.DockerWebClient
import com.swarmbit.docker.api.common.rest.model.ResponseType
import com.swarmbit.docker.api.networks.model.Network
import com.swarmbit.docker.api.networks.model.NetworkCreateResponse
import com.swarmbit.docker.api.networks.parameters.NetworksCreateParameters
import com.swarmbit.docker.api.networks.parameters.NetworksInspectParameters
import com.swarmbit.docker.api.networks.parameters.NetworksListParameters
import io.micronaut.context.annotation.Context

@Context
class NetworksApiImpl(dockerWebClient: DockerWebClient) : AbstractApiImpl(dockerWebClient), NetworksApi {
    companion object {
        private const val NETWORKS_PATH = "/networks"
        private const val CREATE_PATH = "/create"
    }

    override fun listNetworks(swarmId: String, parameters: NetworksListParameters): List<Network> {
        return listObjects(NETWORKS_PATH, swarmId, object : ResponseType<List<Network>>() {}, parameters)
    }

    override fun inspectNetwork(swarmId: String, id: String, parameters: NetworksInspectParameters): Network {
        return inspectObject(NETWORKS_PATH, swarmId, object : ResponseType<Network>() {}, id, parameters)
            ?: throw IllegalArgumentException("No network found for swarmId ($swarmId) and networkId (${id})")
    }

    override fun createNetwork(swarmId: String, parameters: NetworksCreateParameters): NetworkCreateResponse {
        return createObject(NETWORKS_PATH + CREATE_PATH, swarmId, object : ResponseType<NetworkCreateResponse>() {}, parameters)!!
    }

    override fun deleteNetwork(swarmId: String, id: String) {
        deleteObject("$NETWORKS_PATH/$id", swarmId, object : ResponseType<Void>() {})
    }

}
