package com.swarmmanager;

import com.swarmmanager.docker.api.common.client.DockerWebClientProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@EnableConfigurationProperties(DockerWebClientProperties.class)
@PropertySource("classpath:docker.properties")
@SpringBootApplication
public class SwarmManagerControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SwarmManagerControllerApplication.class, args);
    }

}
