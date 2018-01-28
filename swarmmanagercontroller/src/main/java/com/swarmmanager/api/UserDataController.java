package com.swarmmanager.api;

import com.swarmmanager.auth.Role;
import com.swarmmanager.repository.model.User;
import com.swarmmanager.repository.model.UserData;
import com.swarmmanager.repository.UserDataRepository;
import com.swarmmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.swarmmanager.util.UserUtil.getCurrentUsername;

@RestController
@RequestMapping("/api/userData")
public class UserDataController {

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserRepository userRepository;

    @PreAuthorize(Role.IS_VISITOR)
    @RequestMapping(method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserData getUserData() {
        User user = userRepository.findByUsername(getCurrentUsername());
        UserData userData = userDataRepository.findByUsername(getCurrentUsername());
        userData.setRoles(user.getRoles());
        return userData;
    }
}
