package com.swarmmanager.docker.api.common.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiMinVersion;
import com.swarmmanager.docker.api.common.json.inner.IPAMJson;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
    @JsonProperty("Created")
    private String created;

    @JsonProperty("Scope")
    private String scope;

    @JsonProperty("Driver")
    private String driver;

    @JsonProperty("Attachable")
    private Boolean attachable;

    @JsonProperty("Internal")
    private Boolean internal;

    @JsonProperty("EnableIPv6")
    private Boolean enableIPv6;

    @JsonProperty("CheckDuplicate")
    private Boolean checkDuplicate;

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

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public Boolean isAttachable() {
        return attachable;
    }

    public void setAttachable(Boolean attachable) {
        this.attachable = attachable;
    }

    public Boolean isInternal() {
        return internal;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }

    public Boolean isEnableIPv6() {
        return enableIPv6;
    }

    public void setEnableIPv6(Boolean enableIPv6) {
        this.enableIPv6 = enableIPv6;
    }

    public Boolean isCheckDuplicate() {
        return checkDuplicate;
    }

    public void setCheckDuplicate(Boolean checkDuplicate) {
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
        sb.append(", created='").append(created).append('\'');
        sb.append(", scope='").append(scope).append('\'');
        sb.append(", driver='").append(driver).append('\'');
        sb.append(", attachable=").append(attachable);
        sb.append(", internal=").append(internal);
        sb.append(", enableIPv6=").append(enableIPv6);
        sb.append(", checkDuplicate=").append(checkDuplicate);
        sb.append(", ipam=").append(ipam);
        sb.append('}');
        return sb.toString();
    }
}
