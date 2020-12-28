package com.swarmbit.docker.api.common.client

import javax.ws.rs.client.WebTarget

interface DockerWebClient {

    fun getBaseResource(): WebTarget

    fun getBaseResource(id: String): WebTarget
}
