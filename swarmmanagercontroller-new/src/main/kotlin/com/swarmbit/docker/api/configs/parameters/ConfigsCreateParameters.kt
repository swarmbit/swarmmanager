package com.swarmbit.docker.api.configs.parameters

import com.swarmbit.docker.api.common.parameters.RequestBodyParameter
import com.swarmbit.docker.api.configs.model.ConfigSpec

class ConfigsCreateParameters : RequestBodyParameter {

    private var config: ConfigSpec = ConfigSpec()

    fun setConfig(config: ConfigSpec): ConfigsCreateParameters {
        this.config = config
        return this
    }

    override val requestBody: Any
        get() = config
}
