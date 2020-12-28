package com.swarmbit.rest

import com.swarmbit.docker.api.secrets.SecretsApi
import com.swarmbit.docker.api.secrets.model.Secret
import com.swarmbit.docker.api.secrets.parameters.SecretsListParameters
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller("secrets")
class SecretsController(private val secretsApi: SecretsApi) {

    @Get
    fun getSecrets(): List<Secret> {
        return secretsApi.listSecrets("swarm", SecretsListParameters())
    }
}
