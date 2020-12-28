package com.swarmbit.docker.api.common.client.jaxrs.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.swarmbit.docker.api.common.exception.DockerRemoteApiException
import mu.KotlinLogging
import java.io.IOException
import javax.annotation.Priority
import javax.ws.rs.client.ClientRequestContext
import javax.ws.rs.client.ClientResponseContext
import javax.ws.rs.client.ClientResponseFilter

private val logger = KotlinLogging.logger {}
private val objectMapper = ObjectMapper()

@Priority(5000)
class ResponseStatusExceptionFilter : ClientResponseFilter {

    @Throws(IOException::class)
    override fun filter(requestContext: ClientRequestContext, responseContext: ClientResponseContext) {
        val status = responseContext.status
        if (status >= 300) {
            if (status == 503) {
                throw DockerRemoteApiException("Configured node is not part of a swarm.", status)
            }
            var errorString = "Docker api error"
            if (responseContext.hasEntity()) {
                val entityString = readEntity(responseContext)
                errorString = objectMapper.readTree(entityString)["message"].asText()
                logger.debug { "DOCKER API ERROR: $entityString" }
            }
            throw DockerRemoteApiException(errorString, status)
        }
    }

}