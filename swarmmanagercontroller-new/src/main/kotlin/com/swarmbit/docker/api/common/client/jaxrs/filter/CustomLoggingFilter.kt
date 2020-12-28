package com.swarmbit.docker.api.common.client.jaxrs.filter

import mu.KotlinLogging
import javax.annotation.Priority
import javax.ws.rs.client.ClientRequestContext
import javax.ws.rs.client.ClientRequestFilter
import javax.ws.rs.client.ClientResponseContext
import javax.ws.rs.client.ClientResponseFilter

private val logger = KotlinLogging.logger {}

@Priority(6000)
class CustomLoggingFilter : ClientRequestFilter, ClientResponseFilter {

    override fun filter(requestContext: ClientRequestContext) {
        logger.debug { "HTTP REQUEST ---" }
        logger.debug { " - Path: " + requestContext.uri.path }
        logger.debug { " - Header: " + requestContext.headers }
        logger.debug { " - Method: " + requestContext.method }
        requestContext.entity?.let {
            logger.debug { " - Entity: $it" }
        }
    }

    override fun filter(requestContext: ClientRequestContext, responseContext: ClientResponseContext) {
        logger.debug { "HTTP RESPONSE ---" }
        logger.debug { " - Path: " + requestContext.uri.path }
        logger.debug { " - Header: " + responseContext.headers }
        logger.debug { " - Method: " + requestContext.method }
        if (responseContext.hasEntity()) logger.debug { " - Entity: ${readEntity(responseContext)}" }
    }

}