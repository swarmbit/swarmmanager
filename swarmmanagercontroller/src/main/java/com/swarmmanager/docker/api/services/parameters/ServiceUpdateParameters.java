package com.swarmmanager.docker.api.services.parameters;

import com.swarmmanager.docker.api.common.json.ServiceSpecJson;
import com.swarmmanager.rest.HeaderParam;
import com.swarmmanager.rest.QueryParam;

import java.util.Optional;

public class ServiceUpdateParameters {

    private static final String X_REGISTRY_AUTH_HEADER = "X-Registry-Auth";

    private static final String VERSION_NAME = "version";

    private static final String REGISTRY_AUTH_FORM_NAME = "registryAuthFrom";

    private String id;

    private ServiceSpecJson service;

    private QueryParam versionQueryParam;

    private QueryParam registryAuthFromQueryParam;

    private Optional<HeaderParam> XRegistryAuthHeader;

    public ServiceUpdateParameters() {
        service = new ServiceSpecJson();
        this.versionQueryParam = new QueryParam(VERSION_NAME, 0L);
        this.registryAuthFromQueryParam = new QueryParam(REGISTRY_AUTH_FORM_NAME,"spec");
        this.XRegistryAuthHeader = Optional.empty();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServiceSpecJson getService() {
        return service;
    }

    public void setService(ServiceSpecJson service) {
        this.service = service;
    }

    public QueryParam getVersionQueryParam() {
        return versionQueryParam;
    }

    public void setVersionQueryParam(long versionValue) {
        this.versionQueryParam = new QueryParam(VERSION_NAME, versionValue);
    }

    public QueryParam getRegistryAuthFromQueryParam() {
        return registryAuthFromQueryParam;
    }

    public void setRegistryAuthFromQueryParam(String registryAuthFromValue) {
        this.registryAuthFromQueryParam = new QueryParam(REGISTRY_AUTH_FORM_NAME, registryAuthFromValue);
    }

    public Optional<HeaderParam> getXRegistryAuthHeader() {
        return XRegistryAuthHeader;
    }

    public void setXRegistryAuthHeader(String XRegistryAuthValue) {
        this.XRegistryAuthHeader = Optional.of(new HeaderParam(X_REGISTRY_AUTH_HEADER, XRegistryAuthValue));
    }
}
