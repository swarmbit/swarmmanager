package com.swarmbit.docker.api.client.jaxrs

import com.swarmbit.docker.api.config.DockerClientConfig
import org.apache.http.config.Registry
import org.apache.http.conn.socket.ConnectionSocketFactory
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager

class CustomPoolingHttpClientConnectionManager(
        config: DockerClientConfig,
        registry: Registry<ConnectionSocketFactory>
) : PoolingHttpClientConnectionManager(registry) {

    override fun close() {
        super.shutdown()
    }

    override fun shutdown() {
        // Disable shutdown of the pool. This will be done later, when this factory is closed
        // This is a workaround for finalize method on jerseys ClientRuntime which
        // closes the client and shuts down the connection pool when it is garbage collected
    }

    init {
        config.maxTotalConnections?.let {
            maxTotal = it
        }
        config.maxPerRouteConnections?.let {
            defaultMaxPerRoute = it
        }
    }
}