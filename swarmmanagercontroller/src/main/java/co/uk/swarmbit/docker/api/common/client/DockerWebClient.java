package co.uk.swarmbit.docker.api.common.client;

import javax.ws.rs.client.WebTarget;

public interface DockerWebClient {

    WebTarget getBaseResource();

    WebTarget getBaseResource(String id);
}
