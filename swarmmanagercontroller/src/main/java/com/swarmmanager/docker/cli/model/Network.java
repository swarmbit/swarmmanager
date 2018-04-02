package com.swarmmanager.docker.cli.model;

import java.util.List;
import java.util.Map;

public class Network {

    private String id;

    private String name;

    private Long created;

    private Map<String, String> labels;

    private Map<String, String> options;

    private String driver;

    private String ipamDriver;

    private List<IpamConfig> ipamConfigs;

    private Map<String, String> ipamOptions;

    private Boolean ipv6;

    private Boolean internal;

    private Boolean attachable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long createdAt) {
        this.created = createdAt;
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

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getIpamDriver() {
        return ipamDriver;
    }

    public void setIpamDriver(String ipamDriver) {
        this.ipamDriver = ipamDriver;
    }

    public Map<String, String> getIpamOptions() {
        return ipamOptions;
    }

    public void setIpamOptions(Map<String, String> ipamOptions) {
        this.ipamOptions = ipamOptions;
    }

    public Boolean getIpv6() {
        return ipv6;
    }

    public void setIpv6(Boolean ipv6) {
        this.ipv6 = ipv6;
    }

    public Boolean getInternal() {
        return internal;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }

    public Boolean getAttachable() {
        return attachable;
    }

    public void setAttachable(Boolean attachable) {
        this.attachable = attachable;
    }

    public List<IpamConfig> getIpamConfigs() {
        return ipamConfigs;
    }

    public void setIpamConfigs(List<IpamConfig> ipamConfigs) {
        this.ipamConfigs = ipamConfigs;
    }

}
