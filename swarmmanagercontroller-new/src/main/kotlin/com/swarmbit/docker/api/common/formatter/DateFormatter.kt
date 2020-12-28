package com.swarmbit.docker.api.common.formatter

import java.time.Duration
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

private const val DOCKER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.nX"
private const val DOCKER_DATE_FORMAT_2 = "yyyy-MM-dd'T'HH:mm:ssX"
private const val DOCKER_DATE_FORMAT_3 = "yyyy-MM-dd'T'HH:mm:ss.nVV"
private val formatter = DateTimeFormatter.ofPattern(DOCKER_DATE_FORMAT)
private val formatter2 = DateTimeFormatter.ofPattern(DOCKER_DATE_FORMAT_2)
private val formatter3 = DateTimeFormatter.ofPattern(DOCKER_DATE_FORMAT_3)

fun String.dockerDateToDuration(): Duration = Duration.between(LocalDateTime.parse(this, formatter), LocalDateTime.now())

fun String.dockerDateToZonedDateTime(): ZonedDateTime =
    try {
        ZonedDateTime.parse(this, formatter)
    } catch (exception: DateTimeParseException) {
        try {
            ZonedDateTime.parse(this, formatter2)
        } catch (exception2: DateTimeParseException) {
            try {
                ZonedDateTime.parse(this, formatter3)
            } catch (e: Exception) {
                throw IllegalStateException()
            }
        }
    }

fun String.dockerToEpochMillis(): Long = this.dockerDateToZonedDateTime().toInstant().toEpochMilli()
