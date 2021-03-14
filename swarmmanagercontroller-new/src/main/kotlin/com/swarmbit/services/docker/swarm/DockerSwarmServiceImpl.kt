package com.swarmbit.services.docker.swarm

import com.swarmbit.docker.api.swarm.SwarmApi
import com.swarmbit.docker.api.swarm.model.SwarmSpec
import com.swarmbit.docker.api.swarm.parameters.SwarmUpdateParameters
import com.swarmbit.services.docker.swarm.model.DockerSwarm
import com.swarmbit.services.docker.swarm.model.DockerSwarmUpdate
import com.swarmbit.services.docker.swarm.model.toDockerSwarm
import com.swarmbit.services.docker.swarm.model.toSwarmSpec
import io.micronaut.context.annotation.Context

@Context
class DockerSwarmServiceImpl(
    private val swarmApi: SwarmApi
) : DockerSwarmService {

    override fun update(swarmId: String, dockerSwarmUpdate: DockerSwarmUpdate) {
        swarmApi.inspectSwarm(swarmId).let {
            swarmApi.updateSwarm(
                swarmId,
                SwarmUpdateParameters()
                    .setVersionQueryParam(it.version?.index ?: 0 + 1L)
                    .setSpec(dockerSwarmUpdate.toSwarmSpec(it.spec ?: SwarmSpec()))
            )
        }
    }

    override fun inspect(swarmId: String): DockerSwarm {
        return swarmApi.inspectSwarm(swarmId).toDockerSwarm()
    }

    override fun unlock(swarmId: String) {
        swarmApi.unlock(swarmId)
    }

    override fun rotate(swarmId: String) {
        val swarm = swarmApi.inspectSwarm(swarmId)
        val parameters = SwarmUpdateParameters()
            .setRotateManagerTokenQueryParam(true)
            .setRotateManagerUnlockKeyQueryParam(true)
            .setRotateWorkerTokenQueryParam(true)
            .setVersionQueryParam(swarm.version?.index ?: 0 + 1L)
        swarmApi.updateSwarm(swarmId, parameters)
    }
}
