package co.uk.swarmbit.docker.api.services.parameters;

import co.uk.swarmbit.docker.api.common.json.ServiceSpecJson;
import co.uk.swarmbit.docker.api.common.parameters.HeaderParameters;
import co.uk.swarmbit.docker.api.common.parameters.RequestBodyParameter;
import co.uk.swarmbit.rest.HeaderParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceCreateParameters implements RequestBodyParameter, HeaderParameters {

    private static final String X_REGISTRY_AUTH_HEADER_NAME = "X-Registry-Auth";

    private Optional<HeaderParam> XRegistryAuthHeader;

    private ServiceSpecJson service;

    public  ServiceCreateParameters() {
        service = new ServiceSpecJson();
        XRegistryAuthHeader = Optional.empty();
    }

    public ServiceCreateParameters setXRegistryAuthHeader(String XRegistryAuthValue) {
        this.XRegistryAuthHeader = Optional.of(new HeaderParam(X_REGISTRY_AUTH_HEADER_NAME, XRegistryAuthValue));
        return this;
    }

    public ServiceCreateParameters setService(ServiceSpecJson service) {
        this.service = service;
        return this;
    }

    @Override
    public Object getRequestBody() {
        return service;
    }

    @Override
    public List<HeaderParam> getHeaders() {
        List<HeaderParam> headers = new ArrayList<>();
        XRegistryAuthHeader.ifPresent(headers::add);
        return headers;
    }
}
