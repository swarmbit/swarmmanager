package com.swarmmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDataRepository extends MongoRepository<User, String> {
    UserData findByUsername(String username);
}
