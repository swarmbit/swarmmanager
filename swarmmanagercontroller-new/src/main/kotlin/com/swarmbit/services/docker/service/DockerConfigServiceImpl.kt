package com.swarmbit.services.docker.service

import com.swarmbit.docker.api.configs.ConfigsApi
import com.swarmbit.docker.api.configs.model.ConfigSpec
import com.swarmbit.docker.api.configs.parameters.ConfigsCreateParameters
import com.swarmbit.docker.api.configs.parameters.ConfigsUpdateParameters
import com.swarmbit.services.docker.config.model.DockerConfig
import com.swarmbit.services.docker.config.model.DockerConfigCreate
import com.swarmbit.services.docker.config.model.DockerConfigUpdate
import com.swarmbit.services.docker.config.model.toConfigSpec
import com.swarmbit.services.docker.config.model.toDockerConfig
import io.micronaut.context.annotation.Context

@Context
class DockerConfigServiceImpl(
    private val configsApi: ConfigsApi
) : DockerConfigService {

    override fun create(swarmId: String, dockerConfigCreate: DockerConfigCreate): DockerConfig =
        configsApi.createConfig(
            swarmId,
            ConfigsCreateParameters().setConfig(dockerConfigCreate.toConfigSpec())
        ).let {
            configsApi.inspectConfig(swarmId, it.id.orEmpty()).toDockerConfig()
        }

    override fun rm(swarmId: String, configId: String) =
        configsApi.deleteConfig(swarmId, configId)

    override fun ls(swarmId: String): List<DockerConfig> =
        configsApi.listConfigs(swarmId).map { it.toDockerConfig() }

    override fun inspect(swarmId: String, configId: String): DockerConfig =
        configsApi.inspectConfig(swarmId, configId).toDockerConfig()

    override fun update(swarmId: String, configId: String, dockerConfigUpdate: DockerConfigUpdate): Unit =
        configsApi.inspectConfig(swarmId, configId).let {
            configsApi.updateConfig(
                swarmId,
                configId,
                ConfigsUpdateParameters()
                    .setVersionQueryParam(it.version?.index ?: 0 + 1L)
                    .setConfig(dockerConfigUpdate.toConfigSpec(it.spec ?: ConfigSpec()))
            )
        }
}
