package com.swarmbit.rest

import com.swarmbit.docker.api.swarm.SwarmApi
import com.swarmbit.docker.api.swarm.model.Swarm
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("swarm")
class SwarmController(private val swarmApi: SwarmApi) {

    @Get
    fun getSwarm(): Swarm {
        return swarmApi.inspectSwarm("swarm")
    }

}