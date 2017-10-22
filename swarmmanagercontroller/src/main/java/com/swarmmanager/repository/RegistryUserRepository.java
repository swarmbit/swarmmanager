package com.swarmmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistryUserRepository extends MongoRepository<RegistryUser, String> {

    RegistryUser findByRegistryUsername(String registryUsername);

    RegistryUser findFirstByRegistriesAndUserOwner(String registry, String userOwner);

}
