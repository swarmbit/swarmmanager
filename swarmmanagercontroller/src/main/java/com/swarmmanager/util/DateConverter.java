package com.swarmmanager.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by flaviapenim on 22/Oct/2017.
 */
public class DateConverter {

    public static LocalDateTime dateToLocalDateTime(Date expiration) {
        return expiration.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
