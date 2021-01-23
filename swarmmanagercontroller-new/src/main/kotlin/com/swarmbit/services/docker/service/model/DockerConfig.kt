package com.swarmbit.services.docker.service.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.swarmbit.docker.api.common.formatter.dockerToEpochMillis
import com.swarmbit.docker.api.common.model.Driver
import com.swarmbit.docker.api.configs.model.Config
import com.swarmbit.docker.api.configs.model.ConfigSpec

@JsonInclude(JsonInclude.Include.NON_NULL)
data class DockerConfig(
    val id: String,
    val createdAt: Long,
    val updatedAt: Long,
    val name: String,
    val labels: Map<String, String>,
    val data: String,
    val templatingName: String,
    val templatingOptions: Map<String, String>
)

data class DockerConfigCreate(
    val name: String? = null,
    val labels: Map<String, String>? = null,
    val data: String? = null,
    val templatingName: String? = null,
    val templatingOptions: Map<String, String>? = null
)

data class DockerConfigUpdate(
    val labels: Map<String, String>
)

fun Config.toDockerConfig(): DockerConfig =
    DockerConfig(
        id = this.id.orEmpty(),
        createdAt = this.createdAt?.dockerToEpochMillis() ?: 0L,
        updatedAt = this.updatedAt?.dockerToEpochMillis() ?: 0L,
        name = this.spec?.name.orEmpty(),
        labels = this.spec?.labels.orEmpty(),
        data = this.spec?.data.orEmpty(),
        templatingName = this.spec?.templating?.name.orEmpty(),
        templatingOptions = this.spec?.templating?.options.orEmpty()
    )

fun DockerConfigCreate.toConfigSpec(): ConfigSpec =
    ConfigSpec(
        name = name,
        labels = labels,
        data = data,
        templating = Driver(
            name = templatingName,
            options = templatingOptions
        )
    )

fun DockerConfigUpdate.toConfigSpec(configSpec: ConfigSpec): ConfigSpec = configSpec.copy(labels = labels)
