package com.swarmmanager.repository;

import com.swarmmanager.repository.model.Token;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;

public interface TokenRepository extends MongoRepository<Token, String> {

    Token findFirstByToken(String token);

    void deleteByExpirationBefore(LocalDateTime date);
}
