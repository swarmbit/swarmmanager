package com.swarmmanager.api;

import com.swarmmanager.auth.mongo.Token;
import com.swarmmanager.auth.mongo.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private TokenRepository tokenRepository;

    @RequestMapping(method = RequestMethod.POST, value = "logout", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void logout(@RequestHeader Token token) {
            tokenRepository.insert(token);
    }

}
