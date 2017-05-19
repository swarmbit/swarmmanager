package com.swarmmanager.docker.api.common.client;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.swarmmanager.docker.api.common.client.jaxrs.filter.ResponseStatusExceptionFilter;
import com.swarmmanager.docker.api.common.client.jaxrs.filter.JsonClientFilter;
import com.swarmmanager.docker.api.common.client.jaxrs.filter.SelectiveLoggingFilter;
import com.swarmmanager.exception.UnsupportedConfiguration;
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

@Component
public class DockerWebClientImpl implements DockerWebClient {

    private final Logger LOGGER = LoggerFactory.getLogger(DockerWebClientImpl.class.getName());

    @Autowired
    private DockerWebClientProperties dockerWebClientProperties;

    private WebTarget baseResource;

    @PostConstruct
    public void InitDockerWebClient() throws UnsupportedConfiguration {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.connectorProvider(new ApacheConnectorProvider());
        clientConfig.property(CommonProperties.FEATURE_AUTO_DISCOVERY_DISABLE, true);

        clientConfig.register(ResponseStatusExceptionFilter.class);
        clientConfig.register(JsonClientFilter.class);
        clientConfig.register(JacksonJsonProvider.class);
        clientConfig.register(new SelectiveLoggingFilter(LOGGER, true));

        if (dockerWebClientProperties.getDockerApiReadTimeout().isPresent()) {
            clientConfig.property(ClientProperties.READ_TIMEOUT, dockerWebClientProperties.getDockerApiReadTimeout().get());
        }

        if (dockerWebClientProperties.getDockerApiConnectTimeout().isPresent()) {
            clientConfig.property(ClientProperties.CONNECT_TIMEOUT, dockerWebClientProperties.getDockerApiConnectTimeout().get());
        }

        if (dockerWebClientProperties.getDockerApiConnectionRequestTimeout().isPresent()) {
            clientConfig.property(ApacheClientProperties.REQUEST_CONFIG, RequestConfig.custom()
                    .setConnectionRequestTimeout(dockerWebClientProperties.getDockerApiConnectionRequestTimeout().get()).build());
        }

        DockerWebClientProtocol protocol = DockerWebClientProtocol.getDockerWebClientProtocol(dockerWebClientProperties.getDockerApiProtocol());
        protocol.setProtocolSpecificConfiguration(clientConfig, dockerWebClientProperties);

        clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, protocol.getConnectionManagerForProtocol(dockerWebClientProperties));

        ClientBuilder clientBuilder = ClientBuilder.newBuilder().withConfig(clientConfig);
        Client client = clientBuilder.build();
        baseResource = client.target(protocol.getUrlForProtocol(dockerWebClientProperties)).path(dockerWebClientProperties.getDockerApiVersion());
    }


    public WebTarget getBaseResource() {
        return baseResource;
    }

}
