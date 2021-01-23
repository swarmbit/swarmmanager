package com.swarmbit.services.docker.swarm

import com.swarmbit.services.docker.config.model.DockerConfig
import com.swarmbit.services.docker.config.model.DockerConfigCreate
import com.swarmbit.services.docker.config.model.DockerConfigUpdate

interface DockerConfigService {
    fun create(swarmId: String, dockerConfigCreate: DockerConfigCreate): DockerConfig
    fun rm(swarmId: String, configId: String)
    fun ls(swarmId: String): List<DockerConfig>
    fun inspect(swarmId: String, configId: String): DockerConfig
    fun update(swarmId: String, configId: String, dockerConfigUpdate: DockerConfigUpdate)
}
