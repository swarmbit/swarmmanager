package com.swarmmanager.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public static String getCurrentUsername() {
        return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
