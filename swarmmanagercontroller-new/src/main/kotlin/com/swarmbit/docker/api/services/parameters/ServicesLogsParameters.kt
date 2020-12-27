package com.swarmbit.docker.api.services.parameters

import com.swarmbit.docker.api.common.parameters.QueryParameters
import com.swarmbit.docker.api.common.rest.model.QueryParam

class ServicesLogsParameters : QueryParameters {
    companion object {
        private const val STDOUT_NAME = "stdout"
        private const val STDERR_NAME = "stderr"
        private const val SINCE_NAME = "since"
        private const val TIMESTAMPS_NAME = "timestamps"
        private const val TAIL_NAME = "tail"
        private const val DETAILS_NAME = "details"
    }

    private var stdoutQueryParam = QueryParam(STDOUT_NAME, false)
    private var stderrQueryParam = QueryParam(STDERR_NAME, false)
    private var timestampsQueryParam = QueryParam(TIMESTAMPS_NAME, false)
    private var detailsQueryParam = QueryParam(DETAILS_NAME, false)
    private var sinceQueryParam: QueryParam? = null
    private var tailQueryParam: QueryParam? = null

    fun getStdoutQueryParam(): QueryParam {
        return stdoutQueryParam
    }

    fun setStdoutQueryParam(stdoutValue: Boolean): ServicesLogsParameters {
        stdoutQueryParam = QueryParam(STDOUT_NAME, stdoutValue)
        return this
    }

    fun getStderrQueryParam(): QueryParam {
        return stderrQueryParam
    }

    fun setStderrQueryParam(stderrValue: Boolean): ServicesLogsParameters {
        stderrQueryParam = QueryParam(STDERR_NAME, stderrValue)
        return this
    }

    fun getDetailsQueryParam(): QueryParam {
        return timestampsQueryParam
    }

    fun setDetailsQueryParam(details: Boolean): ServicesLogsParameters {
        detailsQueryParam = QueryParam(DETAILS_NAME, details)
        return this
    }

    fun getTimestampsQueryParam(): QueryParam {
        return timestampsQueryParam
    }

    fun setTimestampsQueryParam(timestampValue: Boolean): ServicesLogsParameters {
        timestampsQueryParam = QueryParam(TIMESTAMPS_NAME, timestampValue)
        return this
    }

    fun getTailQueryParam(): QueryParam? {
        return tailQueryParam
    }

    fun setTailQueryParam(tailValue: Int): ServicesLogsParameters {
        tailQueryParam = QueryParam(TAIL_NAME, tailValue)
        return this
    }

    fun getSinceQueryParam(): QueryParam? {
        return sinceQueryParam
    }

    fun setSinceQueryParam(sinceValue: Long): ServicesLogsParameters {
        sinceQueryParam = QueryParam(SINCE_NAME, sinceValue)
        return this
    }

    override val queryParams: List<QueryParam>
        get() {
            val queryParams = mutableListOf(
                    stdoutQueryParam,
                    stderrQueryParam,
                    detailsQueryParam,
                    timestampsQueryParam
            )

            sinceQueryParam?.let {
                queryParams.add(it)
            }
            tailQueryParam?.let {
                queryParams.add(it)
            }
            return queryParams
        }

}
