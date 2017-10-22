package com.swarmmanager.auth.util;

import com.swarmmanager.auth.mongo.Token;
import com.swarmmanager.auth.mongo.TokenRepository;
import com.swarmmanager.integration.IntegrationTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CleanInvalidTokensTest extends IntegrationTest {

    @Autowired
    private ScheduledTasks scheduledTasks;

    @Autowired
    private TokenRepository tokenRepository;

    @Before
    public void setUp() throws Exception {
        // Create invalid tokens
        tokenRepository.insert(generateNMockTokens(5, LocalDateTime.now().minusHours(12)));
        // Create valid tokens
        tokenRepository.insert(generateNMockTokens(3, LocalDateTime.now().plusHours(2)));
    }

    @Test
    public void cleanInvalidTokens() throws Exception {
        assertTrue(tokenRepository.findAll().size() == 8);
        scheduledTasks.cleanInvalidTokens();
        assertTrue(tokenRepository.findAll().size() == 3);
    }

    @After
    public void tearDown() throws Exception {
        tokenRepository.deleteAll();
    }

    private List<Token> generateNMockTokens(int n, LocalDateTime date) {
        int i = 0;
        List<Token> mockTokens = new ArrayList<>();
        while (i < n) {
            String stringToken = UUID.randomUUID().toString();
            mockTokens.add(new Token(stringToken, date));
            i++;
        }
        return mockTokens;
    }
}
