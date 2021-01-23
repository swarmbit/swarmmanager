package com.swarmbit.rest

import com.swarmbit.services.docker.network.DockerNetworkService
import com.swarmbit.services.docker.network.model.DockerNetwork
import com.swarmbit.services.docker.network.model.DockerNetworkCreate
import com.swarmbit.services.docker.network.model.DockerNetworkSummary
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status

@Controller("/api/swarms/{swarmId}/networks")
class NetworksController(private val networkService: DockerNetworkService) {

    @Get
    fun getNetworks(@PathVariable swarmId: String): List<DockerNetworkSummary> {
        return networkService.ls(swarmId)
    }

    @Post
    fun createNetwork(@PathVariable swarmId: String, @Body dockerNetworkCreate: DockerNetworkCreate): DockerNetwork {
        return networkService.create(swarmId, dockerNetworkCreate)
    }

    @Get("{networkId}")
    fun getNetwork(@PathVariable swarmId: String, @PathVariable networkId: String): DockerNetwork {
        return networkService.inspect(swarmId, networkId)
    }

    @Delete("{networkId}")
    @Status(HttpStatus.NO_CONTENT)
    fun deleteNetwork(@PathVariable swarmId: String, @PathVariable networkId: String) {
        networkService.rm(swarmId, networkId)
    }
}
