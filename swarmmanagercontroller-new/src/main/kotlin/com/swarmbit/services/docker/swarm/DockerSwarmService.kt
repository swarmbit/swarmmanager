package com.swarmbit.services.docker.swarm

import com.swarmbit.services.docker.swarm.model.DockerSwarm
import com.swarmbit.services.docker.swarm.model.DockerSwarmUpdate

interface DockerSwarmService {
    fun update(swarmId: String, dockerSwarmUpdate: DockerSwarmUpdate)
    fun inspect(swarmId: String): DockerSwarm
    fun unlock(swarmId: String)
    fun rotate(swarmId: String)
}
