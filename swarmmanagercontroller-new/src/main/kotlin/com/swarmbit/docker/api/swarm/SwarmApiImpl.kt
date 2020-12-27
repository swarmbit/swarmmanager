package com.swarmbit.docker.api.swarm

import com.swarmbit.docker.api.common.AbstractApiImpl
import com.swarmbit.docker.api.common.client.DockerWebClient
import com.swarmbit.docker.api.common.rest.executor.RestExecutorFactory
import com.swarmbit.docker.api.common.rest.model.Method
import com.swarmbit.docker.api.common.rest.model.Parameters
import com.swarmbit.docker.api.common.rest.model.ResponseType
import com.swarmbit.docker.api.swarm.model.Swarm
import com.swarmbit.docker.api.swarm.model.UnlockKey
import com.swarmbit.docker.api.swarm.parameters.SwarmUpdateParameters
import io.micronaut.context.annotation.Context

@Context
class SwarmApiImpl(private val dockerWebClient: DockerWebClient): AbstractApiImpl(dockerWebClient), SwarmApi {

    companion object {
        private const val SWARM_PATH = "/swarm"
        private const val UNLOCK_PATH = "/unlock"
        private const val UPDATE_PATH = "/update"
    }

    override fun inspectSwarm(swarmId: String): Swarm {
        return inspectObject(SWARM_PATH, swarmId, object : ResponseType<Swarm>() {})
                ?: throw IllegalArgumentException("Swarm not found for swarmId ($swarmId)")
    }

    override fun updateSwarm(swarmId: String, parameters: SwarmUpdateParameters) {
        updateObject(SWARM_PATH + UPDATE_PATH, swarmId, object : ResponseType<Void>() {}, parameters, parameters)
    }

    override fun unlock(swarmId: String): UnlockKey {
        val restParameters: Parameters = Parameters(dockerWebClient.getBaseResource(swarmId)).setPath(SWARM_PATH + UNLOCK_PATH)
        return RestExecutorFactory.createRestExecutor(Method.POST).execute(restParameters, object : ResponseType<UnlockKey>() {})!!
    }

}