package com.swarmmanager.docker.api.common.client;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.swarmmanager.docker.api.common.client.jaxrs.filter.ResponseStatusExceptionFilter;
import com.swarmmanager.docker.api.common.client.jaxrs.filter.JsonClientFilter;
import com.swarmmanager.docker.api.common.client.jaxrs.filter.SelectiveLoggingFilter;
import com.swarmmanager.docker.config.DockerClientConfig;
import com.swarmmanager.docker.config.DockerConfig;
import com.swarmmanager.docker.config.DockerSwarmConfig;
import com.swarmmanager.exception.UnsupportedConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.client.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class DockerWebClientImpl implements DockerWebClient {

    private final Logger LOGGER = LoggerFactory.getLogger(DockerWebClientImpl.class.getName());

    @Autowired
    private DockerConfig dockerConfig;

    private List<DockerSwarmWebTarget> dockerSwarmWebTargetList;

    @PostConstruct
    public void InitDockerWebClient() throws UnsupportedConfiguration {
        List<DockerSwarmConfig> swarms = dockerConfig.getSwarms();
        if (swarms == null || swarms.size() == 0) {
            throw new UnsupportedConfiguration("Requires at least one swarm");
        }
        dockerSwarmWebTargetList = new ArrayList<>();
        swarms.forEach(swarm -> {
            if (swarm.getId() == null) {
                throw new UnsupportedConfiguration("All swarm configuration must have an id");
            }
            DockerClientConfig dockerClientConfig = swarm.getClient();
            if (dockerClientConfig == null) {
                throw new UnsupportedConfiguration("Requires at least one swarm");
            }
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.connectorProvider(new ApacheConnectorProvider());
            clientConfig.property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);

            clientConfig.register(ResponseStatusExceptionFilter.class);
            clientConfig.register(JsonClientFilter.class);
            clientConfig.register(JacksonJsonProvider.class);
            clientConfig.register(new SelectiveLoggingFilter(LOGGER, true));

            if (dockerClientConfig.getReadTimeout().isPresent()) {
                clientConfig.property(ClientProperties.READ_TIMEOUT, dockerClientConfig.getReadTimeout().get());
            }

            if (dockerClientConfig.getConnectTimeout().isPresent()) {
                clientConfig.property(ClientProperties.CONNECT_TIMEOUT, dockerClientConfig.getConnectTimeout().get());
            }

            if (dockerClientConfig.getConnectionRequestTimeout().isPresent()) {
                clientConfig.property(ApacheClientProperties.REQUEST_CONFIG, RequestConfig.custom()
                        .setConnectionRequestTimeout(dockerClientConfig.getConnectionRequestTimeout().get()).build());
            }

            DockerWebClientProtocol protocol = DockerWebClientProtocol.getDockerWebClientProtocol(dockerClientConfig.getProtocol());
            protocol.setProtocolSpecificConfiguration(clientConfig, dockerClientConfig);

            clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, protocol.getConnectionManagerForProtocol(dockerClientConfig));

            ClientBuilder clientBuilder = ClientBuilder.newBuilder().withConfig(clientConfig);
            Client client = clientBuilder.build();

            DockerSwarmWebTarget dockerSwarmWebTarget = new DockerSwarmWebTarget();
            dockerSwarmWebTarget.setId(swarm.getId());
            dockerSwarmWebTarget.setBaseResource(client.target(protocol.getUrlForProtocol(dockerClientConfig)).path(dockerConfig.getApiVersion()));
            dockerSwarmWebTargetList.add(dockerSwarmWebTarget);
        });
    }

    public WebTarget getBaseResource() {
        return dockerSwarmWebTargetList.get(0).getBaseResource();
    }

    public WebTarget getBaseResource(String id) {
        WebTarget baseResource = null;
        for(DockerSwarmWebTarget dockerSwarmWebTarget : dockerSwarmWebTargetList) {
            if (StringUtils.equals(id, dockerSwarmWebTarget.getId())) {
                baseResource = dockerSwarmWebTarget.getBaseResource();
            }
        }
        return baseResource;
    }

    private static class DockerSwarmWebTarget {

        private String id;

        private WebTarget baseResource;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public WebTarget getBaseResource() {
            return baseResource;
        }

        public void setBaseResource(WebTarget baseResource) {
            this.baseResource = baseResource;
        }

    }

}
