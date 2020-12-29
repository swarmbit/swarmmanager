package com.swarmbit.rest

import com.swarmbit.services.docker.secret.DockerSecretService
import com.swarmbit.services.docker.secret.model.DockerSecret
import com.swarmbit.services.docker.secret.model.DockerSecretCreate
import com.swarmbit.services.docker.secret.model.DockerSecretUpdate
import io.micronaut.http.HttpStatus
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Patch
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Status

@Controller("/api/swarms/{swarmId}/secrets")
class SecretsController(private val dockerSecretService: DockerSecretService) {

    @Get
    fun getSecrets(@PathVariable swarmId: String): List<DockerSecret> {
        return dockerSecretService.ls(swarmId)
    }

    @Post
    fun createSecret(@PathVariable swarmId: String, @Body dockerSecretCreate: DockerSecretCreate): DockerSecret {
        return dockerSecretService.create(swarmId, dockerSecretCreate)
    }

    @Get("{secretId}")
    fun getSecret(@PathVariable swarmId: String, @PathVariable secretId: String): DockerSecret {
        return dockerSecretService.inspect(swarmId, secretId)
    }

    @Patch("{secretId}")
    @Status(HttpStatus.NO_CONTENT)
    fun updateSecret(@PathVariable swarmId: String, @PathVariable secretId: String, @Body dockerSecretUpdate: DockerSecretUpdate) {
        dockerSecretService.update(swarmId, secretId, dockerSecretUpdate)
    }

    @Delete("{secretId}")
    @Status(HttpStatus.NO_CONTENT)
    fun deleteSecret(@PathVariable swarmId: String, @PathVariable secretId: String) {
        dockerSecretService.rm(swarmId, secretId)
    }
}
