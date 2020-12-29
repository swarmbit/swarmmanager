package com.swarmbit.rest

import com.swarmbit.services.docker.config.DockerConfigService
import com.swarmbit.services.docker.config.model.DockerConfig
import com.swarmbit.services.docker.config.model.DockerConfigCreate
import com.swarmbit.services.docker.config.model.DockerConfigUpdate
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Patch
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status

@Controller("/api/swarms/{swarmId}/configs")
class ConfigsController(private val dockerConfigService: DockerConfigService) {

    @Get
    fun getConfigs(@PathVariable swarmId: String): List<DockerConfig> {
        return dockerConfigService.ls(swarmId)
    }

    @Post
    fun createConfig(@PathVariable swarmId: String, @Body dockerConfigCreate: DockerConfigCreate): DockerConfig {
        return dockerConfigService.create(swarmId, dockerConfigCreate)
    }

    @Get("{configId}")
    fun getConfig(@PathVariable swarmId: String, @PathVariable configId: String): DockerConfig {
        return dockerConfigService.inspect(swarmId, configId)
    }

    @Patch("{configId}")
    @Status(HttpStatus.NO_CONTENT)
    fun updateConfig(@PathVariable swarmId: String, @PathVariable configId: String, @Body dockerConfigUpdate: DockerConfigUpdate) {
        dockerConfigService.update(swarmId, configId, dockerConfigUpdate)
    }

    @Delete("{configId}")
    @Status(HttpStatus.NO_CONTENT)
    fun deleteConfig(@PathVariable swarmId: String, @PathVariable configId: String) {
        dockerConfigService.rm(swarmId, configId)
    }
}
