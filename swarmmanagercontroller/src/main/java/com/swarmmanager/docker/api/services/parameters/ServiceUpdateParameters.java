package com.swarmmanager.docker.api.services.parameters;

import com.swarmmanager.docker.api.common.parameters.HeaderParameters;
import com.swarmmanager.docker.api.common.parameters.QueryParameters;
import com.swarmmanager.docker.api.common.parameters.RequestBodyParameter;
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

    private static final String ROLLBACK_NAME = "rollback";

    private ServiceSpecJson service;

    private QueryParam versionQueryParam;

    private QueryParam registryAuthFromQueryParam;

    private Optional<QueryParam> rollbackQueryParam;

    private Optional<HeaderParam> XRegistryAuthHeader;

    public ServiceUpdateParameters() {
        service = new ServiceSpecJson();
        this.versionQueryParam = new QueryParam(VERSION_NAME, 0L);
        this.XRegistryAuthHeader = Optional.empty();
        this.rollbackQueryParam = Optional.empty();
    }

    public ServiceUpdateParameters setService(ServiceSpecJson service) {
        this.service = service;
        return this;
    }

    public ServiceUpdateParameters setVersionQueryParam(long versionValue) {
        this.versionQueryParam = new QueryParam(VERSION_NAME, versionValue);
        return this;
    }

    public ServiceUpdateParameters setRollback(Boolean rollback) {
        if (rollback != null && rollback) {
            this.rollbackQueryParam = Optional.of(new QueryParam(ROLLBACK_NAME, "previous"));
        }
        return this;
    }

    public ServiceUpdateParameters setXRegistryAuthHeader(String XRegistryAuthValue) {
        this.XRegistryAuthHeader = Optional.of(new HeaderParam(X_REGISTRY_AUTH_HEADER, XRegistryAuthValue));
        return this;
    }

    public ServiceUpdateParameters setRegistryAuthFromSpec() {
        this.registryAuthFromQueryParam = new QueryParam(REGISTRY_AUTH_FORM_NAME,"spec");
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
        if (registryAuthFromQueryParam != null) {
            queryParams.add(registryAuthFromQueryParam);
        }
        rollbackQueryParam.ifPresent(queryParams::add);
        return queryParams;
    }

    @Override
    public List<HeaderParam> getHeaders() {
        List<HeaderParam> headerParams = new ArrayList<>();
        XRegistryAuthHeader.ifPresent(headerParams::add);
        return headerParams;
    }

}
