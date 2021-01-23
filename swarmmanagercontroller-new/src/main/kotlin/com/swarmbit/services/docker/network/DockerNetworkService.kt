package com.swarmbit.services.docker.network

import com.swarmbit.services.docker.network.model.DockerNetwork
import com.swarmbit.services.docker.network.model.DockerNetworkCreate
import com.swarmbit.services.docker.network.model.DockerNetworkSummary

interface DockerNetworkService {
    fun create(swarmId: String, dockerNetworkCreate: DockerNetworkCreate): DockerNetwork
    fun rm(swarmId: String, networkId: String)
    fun ls(swarmId: String): List<DockerNetworkSummary>
    fun inspect(swarmId: String, networkId: String): DockerNetwork
}
