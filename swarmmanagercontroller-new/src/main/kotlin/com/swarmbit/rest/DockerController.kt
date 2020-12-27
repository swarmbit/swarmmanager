package com.swarmbit.rest

import com.swarmbit.docker.api.configs.ConfigsApi
import com.swarmbit.docker.api.configs.model.Config
import com.swarmbit.docker.api.configs.parameters.ConfigsListParameters
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("configs")
class DockerController(private val configsApi: ConfigsApi) {

    @Get
    fun getConfigs(): List<Config> {
        return configsApi.listConfigs("swarm", ConfigsListParameters())
    }

}