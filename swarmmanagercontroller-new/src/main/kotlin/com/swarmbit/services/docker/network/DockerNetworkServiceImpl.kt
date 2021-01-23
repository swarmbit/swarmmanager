package com.swarmbit.services.docker.network

import com.swarmbit.docker.api.networks.NetworksApi
import com.swarmbit.docker.api.networks.parameters.NetworksCreateParameters
import com.swarmbit.services.docker.network.model.DockerNetwork
import com.swarmbit.services.docker.network.model.DockerNetworkCreate
import com.swarmbit.services.docker.network.model.DockerNetworkSummary
import com.swarmbit.services.docker.network.model.toDockerNetwork
import com.swarmbit.services.docker.network.model.toDockerNetworkSummary
import com.swarmbit.services.docker.network.model.toNetwork
import io.micronaut.context.annotation.Context

@Context
class DockerNetworkServiceImpl(
    private val networkApi: NetworksApi
) : DockerNetworkService {

    override fun create(swarmId: String, dockerNetworkCreate: DockerNetworkCreate): DockerNetwork =
        networkApi.createNetwork(
            swarmId,
            NetworksCreateParameters().setNetwork(dockerNetworkCreate.toNetwork())
        ).let {
            networkApi.inspectNetwork(swarmId, it.id.orEmpty()).toDockerNetwork()
        }

    override fun rm(swarmId: String, networkId: String) =
        networkApi.deleteNetwork(swarmId, networkId)

    override fun ls(swarmId: String): List<DockerNetworkSummary> =
        networkApi.listNetworks(swarmId).map { it.toDockerNetworkSummary() }

    override fun inspect(swarmId: String, networkId: String): DockerNetwork =
        networkApi.inspectNetwork(swarmId, networkId).toDockerNetwork()

}
