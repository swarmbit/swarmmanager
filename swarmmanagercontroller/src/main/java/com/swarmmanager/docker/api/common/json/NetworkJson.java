package com.swarmmanager.docker.api.common.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;
import com.swarmmanager.docker.api.common.json.inner.IPAMJson;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NetworkJson {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Id")
    private String id;

    @JsonProperty("Labels")
    private Map<String, String> labels;

    @JsonProperty("Options")
    private Map<String, String> options;

    @DockerRemoteApiMinVersion("v1.25")
    @JsonProperty("CreatedAt")
    private String createdAt;

    @JsonProperty("Scope")
    private String scope;

    @JsonProperty("Driver")
    private String driver;

    @JsonProperty("Attachable")
    private boolean attachable;

    @JsonProperty("EnableIPv6")
    private boolean enableIPv6;

    @JsonProperty("CheckDuplicate")
    private boolean checkDuplicate;

    @DockerRemoteApiMinVersion("v1.25")
    @JsonProperty("IPAM")
    private IPAMJson ipam;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public boolean isAttachable() {
        return attachable;
    }

    public void setAttachable(boolean attachable) {
        this.attachable = attachable;
    }

    public boolean isEnableIPv6() {
        return enableIPv6;
    }

    public void setEnableIPv6(boolean enableIPv6) {
        this.enableIPv6 = enableIPv6;
    }

    public boolean isCheckDuplicate() {
        return checkDuplicate;
    }

    public void setCheckDuplicate(boolean checkDuplicate) {
        this.checkDuplicate = checkDuplicate;
    }

    public IPAMJson getIpam() {
        return ipam;
    }

    public void setIpam(IPAMJson ipam) {
        this.ipam = ipam;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("NetworkJson{");
        sb.append("name='").append(name).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", labels=").append(labels);
        sb.append(", options=").append(options);
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", scope='").append(scope).append('\'');
        sb.append(", driver='").append(driver).append('\'');
        sb.append(", attachable=").append(attachable);
        sb.append(", enableIPv6=").append(enableIPv6);
        sb.append(", checkDuplicate=").append(checkDuplicate);
        sb.append(", ipam=").append(ipam);
        sb.append('}');
        return sb.toString();
    }
}
