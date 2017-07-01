package com.swarmmanager.docker.api.services.parameters;

import com.swarmmanager.docker.api.common.HeaderParameters;
import com.swarmmanager.docker.api.common.QueryParameters;
import com.swarmmanager.docker.api.common.RequestBodyParameter;
import com.swarmmanager.docker.api.common.json.ServiceSpecJson;
import com.swarmmanager.rest.HeaderParam;
import com.swarmmanager.rest.QueryParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceUpdateParameters implements RequestBodyParameter, HeaderParameters, QueryParameters{

    private static final String X_REGISTRY_AUTH_HEADER = "X-Registry-Auth";

    private static final String VERSION_NAME = "version";

    private static final String REGISTRY_AUTH_FORM_NAME = "registryAuthFrom";

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

    public ServiceUpdateParameters setService(ServiceSpecJson service) {
        this.service = service;
        return this;
    }

    public ServiceUpdateParameters setVersionQueryParam(long versionValue) {
        this.versionQueryParam = new QueryParam(VERSION_NAME, versionValue);
        return this;
    }

    public ServiceUpdateParameters setRegistryAuthFromQueryParam(String registryAuthFromValue) {
        this.registryAuthFromQueryParam = new QueryParam(REGISTRY_AUTH_FORM_NAME, registryAuthFromValue);
        return this;
    }

    public ServiceUpdateParameters setXRegistryAuthHeader(String XRegistryAuthValue) {
        this.XRegistryAuthHeader = Optional.of(new HeaderParam(X_REGISTRY_AUTH_HEADER, XRegistryAuthValue));
        return this;
    }

    @Override
    public Object getRequestBody() {
        return service;
    }

    @Override
    public List<QueryParam> getQueryParams() {
        List<QueryParam> queryParams = new ArrayList<>();
        queryParams.add(versionQueryParam);
        queryParams.add(registryAuthFromQueryParam);
        return queryParams;
    }

    @Override
    public List<HeaderParam> getHeaders() {
        List<HeaderParam> headerParams = new ArrayList<>();
        XRegistryAuthHeader.ifPresent(headerParams::add);
        return headerParams;
    }

}
