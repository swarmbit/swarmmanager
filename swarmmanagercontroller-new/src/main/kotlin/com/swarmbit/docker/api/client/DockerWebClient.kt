package com.swarmbit.docker.api.client

import javax.ws.rs.client.WebTarget

interface DockerWebClient {

    fun getBaseResource(): WebTarget

    fun getBaseResource(id: String): WebTarget

}
