package com.swarmmanager.docker.config;

import com.swarmmanager.integration.IntegrationTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@Category(IntegrationTest.class)
public class DockerConfigTest {

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
