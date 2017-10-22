package com.swarmmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RegistryUserRepository extends MongoRepository<RegistryUser, String> {

    List<RegistryUser> findByUserOwner(String userOwner);

    RegistryUser findByNameAndUserOwner(String name, String userOwner);

    void deleteByNameAndUserOwner(String name, String userOwner);

}
