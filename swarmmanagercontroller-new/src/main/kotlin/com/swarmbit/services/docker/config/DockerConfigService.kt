package com.swarmbit.services.docker.config

import com.swarmbit.services.docker.config.model.SwarmConfig
import com.swarmbit.services.docker.config.model.SwarmConfigCreate
import com.swarmbit.services.docker.config.model.SwarmConfigUpdate

interface DockerConfigService {
    fun create(swarmId: String, swarmConfigCreate: SwarmConfigCreate): SwarmConfig
    fun rm(swarmId: String, configId: String)
    fun ls(swarmId: String): List<SwarmConfig>
    fun inspect(swarmId: String, configId: String): SwarmConfig
    fun update(swarmId: String, configId: String, swarmConfigUpdate: SwarmConfigUpdate)
}
