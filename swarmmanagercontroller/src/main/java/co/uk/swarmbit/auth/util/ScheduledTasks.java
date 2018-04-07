package co.uk.swarmbit.auth.util;

import co.uk.swarmbit.repository.model.Token;
import co.uk.swarmbit.repository.TokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class.getName());

    @Autowired
    private TokenRepository tokenRepository;

    /**
     * This {@link Scheduled} task will be executed evey 24h cleaning any invalid tokens.
     * A {@link Token} is considered invalid if it's expiration date is in the past.
     *
     */
    @Scheduled(fixedRate = 86400000) // 24h
    public void cleanInvalidTokens() {
        LocalDateTime now = LocalDateTime.now();
        tokenRepository.deleteByExpirationBefore(now);
        logger.debug("Deleted all tokens expired before than {}", now);
    }
}
