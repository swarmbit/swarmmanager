package com.swarmmanager.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RegistryRepository extends MongoRepository<Registry, String> {
    Registry findByName(String name);
}
