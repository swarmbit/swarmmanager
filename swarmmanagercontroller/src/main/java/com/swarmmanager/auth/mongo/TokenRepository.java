package com.swarmmanager.auth.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TokenRepository extends MongoRepository<Token, String> {

    Token findFirstByToken(String token);
}
