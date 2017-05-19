package com.swarmmanager.docker.api.common.json.inner;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.swarmmanager.docker.api.common.json.DockerRemoteApiJson;
import com.swarmmanager.docker.api.common.annotation.DockerRemoteApiVersions;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
@DockerRemoteApiVersions(versions = "v1.28")
public class DNSConfigJson implements DockerRemoteApiJson {

    @JsonProperty("Nameservers")
    private String[] nameServers;

    @JsonProperty("Search")
    private String[] search;

    @JsonProperty("Options")
    private String[] options;

    public String[] getNameServers() {
        return nameServers;
    }

    public void setNameServers(String[] nameServers) {
        this.nameServers = nameServers;
    }

    public String[] getSearch() {
        return search;
    }

    public void setSearch(String[] search) {
        this.search = search;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DNSConfigJson{");
        sb.append("nameServers=").append(Arrays.toString(nameServers));
        sb.append(", search=").append(Arrays.toString(search));
        sb.append(", options=").append(Arrays.toString(options));
        sb.append('}');
        return sb.toString();
    }
}
