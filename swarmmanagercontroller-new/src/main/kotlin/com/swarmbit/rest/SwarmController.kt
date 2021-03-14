package com.swarmbit.rest

import com.swarmbit.docker.api.common.config.DockerConfig
import com.swarmbit.services.docker.swarm.DockerSwarmService
import com.swarmbit.services.docker.swarm.model.DockerSwarm
import com.swarmbit.services.docker.swarm.model.DockerSwarmUpdate
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Patch
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status

@Controller("/api/swarms")
class SwarmController(
    private val dockerSwarmService: DockerSwarmService,
    private val dockerConfig: DockerConfig
) {

    @Get
    fun getSwarms(): List<Map<String, String>> {
        return dockerConfig.swarms.map {
            mapOf(
                "id" to it.id,
                "name" to it.name,
                "description" to it.description,
                "apiVersion" to it.apiVersion
            )
        }
    }

    @Get("{swarmId}")
    fun getSwarm(@PathVariable swarmId: String): DockerSwarm {
        return dockerSwarmService.inspect(swarmId)
    }

    @Patch("{swarmId}")
    @Status(HttpStatus.NO_CONTENT)
    fun updateSwarm(@PathVariable swarmId: String, @Body dockerSwarmUpdate: DockerSwarmUpdate) {
        dockerSwarmService.update(swarmId, dockerSwarmUpdate)
    }

    @Post("{swarmId}/rotate")
    @Status(HttpStatus.NO_CONTENT)
    fun rotateSwarm(@PathVariable swarmId: String) {
        dockerSwarmService.rotate(swarmId)
    }

    @Post("{swarmId}/unlock")
    @Status(HttpStatus.NO_CONTENT)
    fun unlock(@PathVariable swarmId: String) {
        dockerSwarmService.unlock(swarmId)
    }
}
