package com.swarmbit.docker.api.common

import com.swarmbit.extensions.urlEncode
import javax.json.Json
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.iterator

abstract class AbstractFilters : Filters {
    private val filterParameters: MutableMap<String, MutableList<String>> = mutableMapOf()

    override fun asUrlJson(): String {
        val jsonObjectBuilder = Json.createObjectBuilder()
        for ((key, values) in filterParameters) {
            val valuesJsonBuilder = Json.createObjectBuilder()
            for (value in values) {
                valuesJsonBuilder.add(value, true)
            }
            jsonObjectBuilder.add(key, valuesJsonBuilder)
        }
        return jsonObjectBuilder.build().toString().urlEncode()
    }

    protected fun addFilter(key: String, value: String): AbstractFilters {
        val values = filterParameters.computeIfAbsent(key) { mutableListOf() }
        values.add(value)
        return this
    }

}