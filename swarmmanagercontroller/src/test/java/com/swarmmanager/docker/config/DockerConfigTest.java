package com.swarmmanager.docker.config;

import com.swarmmanager.integration.IntegrationTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class DockerConfigTest extends IntegrationTest {

    @Autowired
    private DockerConfig dockerConfig;

    @Test
    public void testConfig() {
       List<DockerSwarmConfig> swarms = dockerConfig.getSwarms();
       assertTrue(swarms.size() == 2);
        swarms.forEach((swarm) -> {
            assertTrue(swarm.getClient() != null);
        });
    }

}
