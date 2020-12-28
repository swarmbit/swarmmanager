package com.swarmbit.docker.api.common

import com.swarmbit.extensions.urlEncode
import javax.json.Json
import kotlin.collections.component1
import kotlin.collections.component2

abstract class AbstractFilters : Filters {
    private val filterParameters: MutableMap<String, MutableList<Filter>> = mutableMapOf()

    override fun asUrlJson(): String {
        val jsonObjectBuilder = Json.createObjectBuilder()
        for ((key, values) in filterParameters) {
            val valuesJsonBuilder = Json.createObjectBuilder()
            for (value in values) {
                valuesJsonBuilder.add(value.value, value.equals)
            }
            jsonObjectBuilder.add(key, valuesJsonBuilder)
        }
        return jsonObjectBuilder.build().toString().urlEncode()
    }

    protected fun addFilter(key: String, value: String, equals: Boolean = true): AbstractFilters {
        val values = filterParameters.computeIfAbsent(key) { mutableListOf() }
        values.add(Filter(value, equals))
        return this
    }

}

private data class Filter(val value: String, val equals: Boolean)