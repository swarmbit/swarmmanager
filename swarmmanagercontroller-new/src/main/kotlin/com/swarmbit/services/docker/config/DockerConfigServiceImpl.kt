package com.swarmbit.services.docker.config

import com.swarmbit.docker.api.configs.ConfigsApi
import com.swarmbit.docker.api.configs.model.ConfigSpec
import com.swarmbit.docker.api.configs.parameters.ConfigsCreateParameters
import com.swarmbit.docker.api.configs.parameters.ConfigsUpdateParameters
import com.swarmbit.services.docker.config.model.SwarmConfig
import com.swarmbit.services.docker.config.model.SwarmConfigCreate
import com.swarmbit.services.docker.config.model.SwarmConfigUpdate
import com.swarmbit.services.docker.config.model.toConfigSpec
import com.swarmbit.services.docker.config.model.toSwarmConfig
import io.micronaut.context.annotation.Context

@Context
class DockerConfigServiceImpl(
    private val configsApi: ConfigsApi
) : DockerConfigService {

    override fun create(swarmId: String, swarmConfigCreate: SwarmConfigCreate): SwarmConfig =
        configsApi.createConfig(
            swarmId,
            ConfigsCreateParameters().setConfig(swarmConfigCreate.toConfigSpec())
        ).let {
            configsApi.inspectConfig(swarmId, it.id.orEmpty()).toSwarmConfig()
        }

    override fun rm(swarmId: String, configId: String) =
        configsApi.deleteConfig(swarmId, configId)

    override fun ls(swarmId: String): List<SwarmConfig> =
        configsApi.listConfigs(swarmId).map { it.toSwarmConfig() }

    override fun inspect(swarmId: String, configId: String): SwarmConfig =
        configsApi.inspectConfig(swarmId, configId).toSwarmConfig()

    override fun update(swarmId: String, configId: String, swarmConfigUpdate: SwarmConfigUpdate): Unit =
        configsApi.inspectConfig(swarmId, configId).let {
            configsApi.updateConfig(
                swarmId,
                configId,
                ConfigsUpdateParameters()
                    .setVersionQueryParam(it.version?.index ?: 0 + 1L)
                    .setConfig(swarmConfigUpdate.toConfigSpec(it.spec ?: ConfigSpec()))
            )
        }
}
