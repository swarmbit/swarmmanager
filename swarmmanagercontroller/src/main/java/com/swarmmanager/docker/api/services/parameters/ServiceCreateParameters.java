package com.swarmmanager.docker.api.services.parameters;

import com.swarmmanager.docker.api.common.json.ServiceSpecJson;
import com.swarmmanager.rest.HeaderParam;

import java.util.Optional;

public class ServiceCreateParameters {

    private static final String X_REGISTRY_AUTH_HEADER_NAME = "X-Registry-Auth";

    private Optional<HeaderParam> XRegistryAuthHeader;

    private ServiceSpecJson service;

    public  ServiceCreateParameters() {
        service = new ServiceSpecJson();
        XRegistryAuthHeader = Optional.empty();
    }

    public Optional<HeaderParam> getXRegistryAuthHeader() {
        return XRegistryAuthHeader;
    }

    public void setXRegistryAuthHeader(String XRegistryAuthValue) {
        if (XRegistryAuthValue != null) {
            this.XRegistryAuthHeader = Optional.of(new HeaderParam(X_REGISTRY_AUTH_HEADER_NAME, XRegistryAuthValue));
        }
    }

    public ServiceSpecJson getService() {
        return service;
    }

    public void setService(ServiceSpecJson service) {
        this.service = service;
    }
}
