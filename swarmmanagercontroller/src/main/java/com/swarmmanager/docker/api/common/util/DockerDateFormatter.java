package com.swarmmanager.docker.api.common.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DockerDateFormatter {

    public static final String DOCKER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.nX";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DOCKER_DATE_FORMAT);

    public static Duration fromDateStringToDuration(String dateTime) {
        return Duration.between(LocalDateTime.parse(dateTime, formatter), LocalDateTime.now());
    }

    public static ZonedDateTime fromDateStringToZonedDateTime(String dateTime) {
        return ZonedDateTime.parse(dateTime, formatter);
    }

}
