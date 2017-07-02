package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class IPAMJson {

    @JsonProperty("Driver")
    private String driver;

    @JsonProperty("Options")
    private Map<String, String> Options;

    @JsonProperty("Config")
    private IPAMConfigJson[] config;

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public Map<String, String> getOptions() {
        return Options;
    }

    public void setOptions(Map<String, String> options) {
        Options = options;
    }

    public IPAMConfigJson[] getConfig() {
        return config;
    }

    public void setConfig(IPAMConfigJson[] config) {
        this.config = config;
    }
}
