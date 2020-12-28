package com.swarmbit.docker.api.common.client.jaxrs.filter

import java.io.IOException
import javax.ws.rs.client.ClientRequestContext
import javax.ws.rs.client.ClientResponseContext
import javax.ws.rs.client.ClientResponseFilter
import javax.ws.rs.core.MediaType

class JsonClientFilter : ClientResponseFilter {

    @Throws(IOException::class)
    override fun filter(requestContext: ClientRequestContext, responseContext: ClientResponseContext) {
        if (responseContext.mediaType != null && responseContext.mediaType.isCompatible(MediaType.TEXT_PLAIN_TYPE)) {
            val newContentType = "application/json" + responseContext.mediaType.toString().substring(10)
            responseContext.headers.putSingle("Content-Type", newContentType)
        }
    }
}
