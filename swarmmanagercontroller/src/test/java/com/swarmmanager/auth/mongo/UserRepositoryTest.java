package com.swarmmanager.auth.mongo;

import com.swarmmanager.integration.IntegrationTest;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private static final String USERNAME = "test";


    @Test
    public void testUserRepository() throws Exception {
        User user = new User();
        user.setUsername(USERNAME);
        userRepository.save(user);
        user = userRepository.findByUsername(USERNAME);
        assertNotNull(user);
        assertNotNull(user.getUsername());
    }

    @After
    public void tearDown() throws Exception {
        User user = this.userRepository.findByUsername(USERNAME);
        this.userRepository.delete(user);
    }

}