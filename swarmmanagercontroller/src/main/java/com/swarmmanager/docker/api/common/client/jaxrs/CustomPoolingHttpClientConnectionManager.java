package com.swarmmanager.docker.api.common.client.jaxrs;

import com.swarmmanager.docker.api.common.client.DockerWebClientProperties;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class CustomPoolingHttpClientConnectionManager extends PoolingHttpClientConnectionManager {

    public CustomPoolingHttpClientConnectionManager(DockerWebClientProperties properties, org.apache.http.config.Registry registry) {
        super(registry);
        if (properties.getDockerApiMaxTotalConnections().isPresent()) {
            this.setMaxTotal(properties.getDockerApiMaxTotalConnections().get());
        }
        if (properties.getDockerApiMaxTotalConnections().isPresent()) {
            this.setMaxTotal(properties.getDockerApiMaxTotalConnections().get());
        }
    }
    @Override
    public void close() {
        super.shutdown();
    }

    @Override
    public void shutdown() {
        // Disable shutdown of the pool. This will be done later, when this factory is closed
        // This is a workaround for finalize method on jerseys ClientRuntime which
        // closes the client and shuts down the connection pool when it is garbage collected
    }
}
