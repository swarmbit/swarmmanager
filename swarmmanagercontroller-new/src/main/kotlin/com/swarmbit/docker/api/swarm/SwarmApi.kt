package com.swarmbit.docker.api.swarm

import com.swarmbit.docker.api.swarm.model.Swarm
import com.swarmbit.docker.api.swarm.model.UnlockKey
import com.swarmbit.docker.api.swarm.parameters.SwarmUpdateParameters

interface SwarmApi {
    fun inspectSwarm(swarmId: String): Swarm
    fun updateSwarm(swarmId: String, parameters: SwarmUpdateParameters)
    fun unlock(swarmId: String): UnlockKey
}