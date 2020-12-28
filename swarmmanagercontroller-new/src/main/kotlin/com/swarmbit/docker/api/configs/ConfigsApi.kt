package com.swarmbit.docker.api.configs

import com.swarmbit.docker.api.configs.model.Config
import com.swarmbit.docker.api.configs.model.ConfigCreateResponse
import com.swarmbit.docker.api.configs.parameters.ConfigsCreateParameters
import com.swarmbit.docker.api.configs.parameters.ConfigsListParameters
import com.swarmbit.docker.api.configs.parameters.ConfigsUpdateParameters

interface ConfigsApi {
    fun listConfigs(swarmId: String, parameters: ConfigsListParameters = ConfigsListParameters()): List<Config>
    fun inspectConfig(swarmId: String, id: String): Config
    fun createConfig(swarmId: String, parameters: ConfigsCreateParameters): ConfigCreateResponse
    fun deleteConfig(swarmId: String, id: String)
    fun updateConfig(swarmId: String, id: String, parameters: ConfigsUpdateParameters)
}
