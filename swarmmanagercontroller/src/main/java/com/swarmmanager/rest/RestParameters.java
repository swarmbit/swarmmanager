package com.swarmmanager.rest;

import javax.ws.rs.client.WebTarget;
import java.util.ArrayList;
import java.util.List;

public class RestParameters {

    private final WebTarget baseRequest;

    private final List<QueryParam> queryParams;

    private final List<HeaderParam> headerParams;

    private Object requestBody;

    private String path;

    public RestParameters(WebTarget baseRequest) {
        this.baseRequest = baseRequest;
        this.queryParams = new ArrayList<>();
        this.headerParams = new ArrayList<>();
    }

    public RestParameters setPath(String path) {
        this.path = path;
        return this;
    }

    public RestParameters addHeaderParam(HeaderParam headerParam) {
        headerParams.add(headerParam);
        return this;
    }

    public RestParameters addHeaderParams(List<HeaderParam> headerParams) {
        headerParams.addAll(headerParams);
        return this;
    }

    public RestParameters addQueryParam(QueryParam queryParam) {
        queryParams.add(queryParam);
        return this;
    }

    public RestParameters addQueryParams(List<QueryParam> queryParams) {
        this.queryParams.addAll(queryParams);
        return this;
    }

    public RestParameters setRequestBody(Object requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public WebTarget getBaseRequest() {
        return baseRequest;
    }

    public List<QueryParam> getQueryParams() {
        return queryParams;
    }

    public List<HeaderParam> getHeaderParams() {
        return headerParams;
    }

    public Object getRequestBody() {
        return requestBody;
    }

    public String getPath() {
        return path;
    }

}

