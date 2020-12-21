package com.swarmbit

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
            .args(*args)
            .packages("com.swarmbit")
            .start()
}

