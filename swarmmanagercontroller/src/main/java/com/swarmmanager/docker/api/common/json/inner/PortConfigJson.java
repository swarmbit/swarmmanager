package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PortConfigJson {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Protocol")
    private String protocol;

    @JsonProperty("TargetPort")
    private int targetPort;

    @JsonProperty("PublishedPort")
    private int publishedPort;

    @JsonProperty("PublishMode")
    private String publishMode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getTargetPort() {
        return targetPort;
    }

    public void setTargetPort(int targetPort) {
        this.targetPort = targetPort;
    }

    public int getPublishedPort() {
        return publishedPort;
    }

    public void setPublishedPort(int publishedPort) {
        this.publishedPort = publishedPort;
    }

    public String getPublishMode() {
        return publishMode;
    }

    public void setPublishMode(String publishMode) {
        this.publishMode = publishMode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PortConfigJson{");
        sb.append("name='").append(name).append('\'');
        sb.append(", protocol=").append(protocol);
        sb.append(", targetPort=").append(targetPort);
        sb.append(", publishedPort=").append(publishedPort);
        sb.append(", publishMode=").append(publishMode);
        sb.append('}');
        return sb.toString();
    }
}
