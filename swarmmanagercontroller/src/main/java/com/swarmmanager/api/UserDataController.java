package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.repository.UserData;
import com.swarmmanager.repository.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.swarmmanager.util.UserUtil.getCurrentUsername;

@RestController
@RequestMapping("/api/userData")
public class UserDataController {

    @Autowired
    private UserDataRepository userDataRepository;

    @Secured(Role.VISITOR)
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserData getUserData() {
        return userDataRepository.findByUsername(getCurrentUsername());
    }
}
