package com.swarmbit.docker.api.configs

import com.swarmbit.docker.api.common.AbstractApiImpl
import com.swarmbit.docker.api.common.client.DockerWebClient
import com.swarmbit.docker.api.common.rest.model.ResponseType
import com.swarmbit.docker.api.configs.model.Config
import com.swarmbit.docker.api.configs.model.ConfigCreateResponse
import com.swarmbit.docker.api.configs.parameters.ConfigsCreateParameters
import com.swarmbit.docker.api.configs.parameters.ConfigsListParameters
import com.swarmbit.docker.api.configs.parameters.ConfigsUpdateParameters
import io.micronaut.context.annotation.Context

@Context
class ConfigsApiImpl(dockerWebClient: DockerWebClient) : AbstractApiImpl(dockerWebClient), ConfigsApi {

    companion object {
        private const val CONFIGS_PATH = "/configs"
        private const val CREATE_PATH = "/create"
        private const val UPDATE_PATH = "/update"
    }

    override fun listConfigs(swarmId: String, parameters: ConfigsListParameters): List<Config> {
        return listObjects(CONFIGS_PATH, swarmId, object : ResponseType<List<Config>>() {}, parameters)
    }

    override fun inspectConfig(swarmId: String, id: String): Config {
        return inspectObject(CONFIGS_PATH, swarmId, object : ResponseType<Config>() {}, id)
            ?: throw IllegalArgumentException("No config found for swarmId ($swarmId) and configId ($id)")
    }

    override fun createConfig(swarmId: String, parameters: ConfigsCreateParameters): ConfigCreateResponse {
        return createObject(CONFIGS_PATH + CREATE_PATH, swarmId, object : ResponseType<ConfigCreateResponse>() {}, parameters)!!
    }

    override fun deleteConfig(swarmId: String, id: String) {
        deleteObject<Void>("$CONFIGS_PATH/$id", swarmId, object : ResponseType<Void>() {})
    }

    override fun updateConfig(swarmId: String, id: String, parameters: ConfigsUpdateParameters) {
        updateObject("$CONFIGS_PATH/$id$UPDATE_PATH", swarmId, object : ResponseType<Void>() {}, parameters, parameters)
    }
}
