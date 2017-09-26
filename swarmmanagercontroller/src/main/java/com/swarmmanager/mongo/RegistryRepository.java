package com.swarmmanager.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistryRepository extends MongoRepository<Registry, String> {
    Registry findByName(String name);
}
