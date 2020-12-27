package com.swarmbit.docker.api.common.rest.model

import javax.ws.rs.client.WebTarget

class Parameters(val baseRequest: WebTarget) {
    private val queryParams: MutableList<QueryParam> = mutableListOf()
    private val headerParams: MutableList<HeaderParam> = mutableListOf()

    var requestBody: Any? = null
        private set

    var path: String? = null
        private set

    fun setPath(path: String): Parameters {
        this.path = path
        return this
    }

    fun addHeaderParam(headerParam: HeaderParam): Parameters {
        headerParams.add(headerParam)
        return this
    }

    fun addHeaderParams(headerParams: List<HeaderParam>): Parameters {
        this.headerParams.addAll(headerParams)
        return this
    }

    fun addQueryParam(queryParam: QueryParam): Parameters {
        queryParams.add(queryParam)
        return this
    }

    fun addQueryParams(queryParams: List<QueryParam>): Parameters {
        this.queryParams.addAll(queryParams)
        return this
    }

    fun setRequestBody(requestBody: Any): Parameters {
        this.requestBody = requestBody
        return this
    }

    fun getQueryParams(): List<QueryParam> {
        return queryParams
    }

    fun getHeaderParams(): List<HeaderParam> {
        return headerParams
    }

}