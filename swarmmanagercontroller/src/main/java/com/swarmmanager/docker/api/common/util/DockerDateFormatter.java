package com.swarmmanager.docker.api.common.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DockerDateFormatter {

    private static final String DOCKER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.nX";

    private static final String DOCKER_DATE_FORMAT_2 = "yyyy-MM-dd'T'HH:mm:ssX";

    private static final String DOCKER_DATE_FORMAT_3 = "yyyy-MM-dd'T'HH:mm:ss.nVV";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DOCKER_DATE_FORMAT);

    private static final DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern(DOCKER_DATE_FORMAT_2);

    private static final DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern(DOCKER_DATE_FORMAT_3);

    public static Duration fromDateStringToDuration(String dateTime) {
        return Duration.between(LocalDateTime.parse(dateTime, formatter), LocalDateTime.now());
    }

    public static ZonedDateTime fromDateStringToZonedDateTime(String dateTime) {
        ZonedDateTime parsedDate;
        try {
            parsedDate = ZonedDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException exception) {
            try {
                parsedDate = ZonedDateTime.parse(dateTime, formatter2);
            } catch (DateTimeParseException exception2) {
                parsedDate = ZonedDateTime.parse(dateTime, formatter3);
            }
        }
        return parsedDate;
    }

}
