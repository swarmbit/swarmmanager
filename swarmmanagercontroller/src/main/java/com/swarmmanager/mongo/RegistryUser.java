package com.swarmmanager.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "registryUsers")
public class RegistryUser {

    @Id
    private String registryUsername;

    private String registryPassword;

    private List<String> registries;

    private String userOwner;

    public String getRegistryUsername() {
        return registryUsername;
    }

    public void setRegistryUsername(String registryUsername) {
        this.registryUsername = registryUsername;
    }

    public String getRegistryPassword() {
        return registryPassword;
    }

    public void setRegistryPassword(String registryPassword) {
        this.registryPassword = registryPassword;
    }

    public List<String> getRegistries() {
        return registries;
    }

    public void setRegistries(List<String> registries) {
        this.registries = registries;
    }

    public String getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(String userOwner) {
        this.userOwner = userOwner;
    }
}
