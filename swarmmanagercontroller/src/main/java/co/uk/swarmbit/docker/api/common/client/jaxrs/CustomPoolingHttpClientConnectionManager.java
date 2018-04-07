package co.uk.swarmbit.docker.api.common.client.jaxrs;

import co.uk.swarmbit.docker.config.DockerClientConfig;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class CustomPoolingHttpClientConnectionManager extends PoolingHttpClientConnectionManager {

    public CustomPoolingHttpClientConnectionManager(DockerClientConfig config, org.apache.http.config.Registry registry) {
        super(registry);
        if (config.getMaxTotalConnections().isPresent()) {
            this.setMaxTotal(config.getMaxTotalConnections().get());
        }
        if (config.getMaxTotalConnections().isPresent()) {
            this.setMaxTotal(config.getMaxTotalConnections().get());
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
