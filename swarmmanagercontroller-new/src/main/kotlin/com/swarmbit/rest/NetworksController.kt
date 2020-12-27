package com.swarmbit.rest

import com.swarmbit.docker.api.networks.NetworksApi
import com.swarmbit.docker.api.networks.model.Network
import com.swarmbit.docker.api.networks.parameters.NetworksListParameters
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("networks")
class NetworksController(private val networksApi: NetworksApi) {

    @Get
    fun getNetworks(): List<Network> {
        return networksApi.listNetworks("swarm", NetworksListParameters())
    }

}