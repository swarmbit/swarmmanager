package com.swarmmanager.docker.api.common.json.inner;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeDescriptionJson {

    @JsonProperty("Hostname")
    private String hostname;

    @JsonProperty("Platform")
    private PlatformJson platform;

    @JsonProperty("Resources")
    private ResourcesJson resources;

    @JsonProperty("Engine")
    private EngineDescriptionJson engine;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public PlatformJson getPlatform() {
        return platform;
    }

    public void setPlatform(PlatformJson platform) {
        this.platform = platform;
    }

    public ResourcesJson getResources() {
        return resources;
    }

    public void setResources(ResourcesJson resources) {
        this.resources = resources;
    }

    public EngineDescriptionJson getEngine() {
        return engine;
    }

    public void setEngine(EngineDescriptionJson engine) {
        this.engine = engine;
    }
}
