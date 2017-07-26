package com.swarmmanager.api;

import com.swarmmanager.auth.mongo.User;
import com.swarmmanager.auth.mongo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST, value = "logout", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void logout(@RequestBody User user) {
        user = userRepository.findByUsername(user.getUsername());
        if (user != null) {
            user.setSecret(null);
            userRepository.save(user);
        }
    }

}
