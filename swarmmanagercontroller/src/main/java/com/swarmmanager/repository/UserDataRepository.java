package com.swarmmanager.repository;

import com.swarmmanager.repository.model.UserData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserDataRepository extends MongoRepository<UserData, String> {
    UserData findByUsername(String username);
}
