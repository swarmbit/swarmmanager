package com.swarmmanager.util;

import org.junit.Test;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static junit.framework.TestCase.assertTrue;

public class DockerDateFormatterTest {

    private static final String NETWORK_ZONE_DATE = "2017-09-21T21:03:32.663679801+01:00";

    private static final String NETWORK_UTC_DATE = "2017-09-21T20:03:32.663679801Z";

    @Test
    public void testDateFormatter () {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nVV");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.nX");
        ZonedDateTime zonedDateTime = ZonedDateTime.parse(NETWORK_ZONE_DATE, formatter);
        ZonedDateTime zonedDateTime2 = ZonedDateTime.parse(NETWORK_UTC_DATE, formatter2);
        assertTrue(zonedDateTime.toInstant().toEpochMilli() == zonedDateTime2.toInstant().toEpochMilli());
    }
}
