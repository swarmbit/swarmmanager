package com.swarmmanager.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistryUserRepository extends MongoRepository<RegistryUser, String> {

    RegistryUser findByRegistryUsername(String registryUsername);

    RegistryUser findFirstByRegistryAndUserOwner(String registry, String userOwner);

}
