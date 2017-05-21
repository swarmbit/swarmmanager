package com.swarmmanager.auth.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
    User findByUsername(String username);
}
