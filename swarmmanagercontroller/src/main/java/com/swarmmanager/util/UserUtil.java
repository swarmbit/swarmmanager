package com.swarmmanager.util;


import com.swarmmanager.repository.User;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public static String getCurrentUsername() {
        return ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }
}
