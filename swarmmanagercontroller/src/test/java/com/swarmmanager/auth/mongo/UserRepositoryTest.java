package com.swarmmanager.auth.mongo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private static final String USERNAME = "test";

    @Before
    public void setUp() throws Exception {
        User user = new User();
        user.setUsername(USERNAME);
        user.setPassword("test");
        assertNull(user.getId());
        userRepository.save(user);
        assertNotNull(user.getId());
    }

    @Test
    public void testFetchData(){
        User user = userRepository.findByUsername(USERNAME);
        assertNotNull(user);
    }

    @After
    public void tearDown() throws Exception {
        User user = this.userRepository.findByUsername(USERNAME);
        this.userRepository.delete(user);
    }

}
