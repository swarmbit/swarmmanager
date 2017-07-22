package com.swarmmanager.docker.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
@ConfigurationProperties(prefix="docker")
public class DockerConfig {

    private String apiVersion;

    private List<DockerSwarmConfig> swarms;

    public DockerConfig() {
        swarms = new ArrayList<>();
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public List<DockerSwarmConfig> getSwarms() {
        return swarms;
    }

    public void setSwarms(List<DockerSwarmConfig> swarms) {
        this.swarms = swarms;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DockerConfig{");
        sb.append("apiVersion='").append(apiVersion).append('\'');
        sb.append(", swarms=").append(swarms);
        sb.append('}');
        return sb.toString();
    }
}
