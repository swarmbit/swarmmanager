package com.swarmbit.docker.api.common.client.jaxrs.filter

import java.io.BufferedInputStream
import java.io.IOException
import javax.ws.rs.client.ClientResponseContext
import kotlin.math.max
import kotlin.math.min

private const val DEFAULT_MAX_ENTITY_SIZE = 8 * 1024

@Throws(IOException::class)
fun readEntity(responseContext: ClientResponseContext): String {
    var stream = responseContext.entityStream
    if (!stream.markSupported()) {
        stream = BufferedInputStream(stream)
    }
    stream.mark(DEFAULT_MAX_ENTITY_SIZE + 1)

    val entity = ByteArray(DEFAULT_MAX_ENTITY_SIZE + 1)
    val entitySize = max(0, stream.read(entity))

    val sb = StringBuilder()
    sb.append(String(entity, 0, min(entitySize, DEFAULT_MAX_ENTITY_SIZE)))

    if (entitySize > DEFAULT_MAX_ENTITY_SIZE) {
        sb.append("...more...")
    }
    sb.append('\n')

    stream.reset()
    responseContext.entityStream = stream
    return sb.toString()
}
