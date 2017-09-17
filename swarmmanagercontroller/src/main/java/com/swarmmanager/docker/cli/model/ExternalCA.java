package com.swarmmanager.docker.cli.model;

import java.util.Map;

public class ExternalCA {

    private String protocol;

    private String url;

    private Map<String, String> options;

    public ExternalCA() {
        this.protocol = "cfssl";
    }

    public String getProtocol() {
        return protocol;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getOptions() {
        return options;
    }

    public void setOptions(Map<String, String> options) {
        this.options = options;
    }
}
