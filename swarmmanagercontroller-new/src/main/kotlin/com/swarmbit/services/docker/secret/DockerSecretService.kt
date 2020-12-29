package com.swarmbit.services.docker.secret

import com.swarmbit.services.docker.secret.model.DockerSecret
import com.swarmbit.services.docker.secret.model.DockerSecretCreate
import com.swarmbit.services.docker.secret.model.DockerSecretUpdate

interface DockerSecretService {
    fun create(swarmId: String, dockerSecretCreate: DockerSecretCreate): DockerSecret
    fun rm(swarmId: String, secretId: String)
    fun ls(swarmId: String): List<DockerSecret>
    fun inspect(swarmId: String, secretId: String): DockerSecret
    fun update(swarmId: String, secretId: String, dockerSecretUpdate: DockerSecretUpdate)
}
