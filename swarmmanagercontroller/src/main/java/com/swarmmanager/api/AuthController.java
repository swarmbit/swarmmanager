package com.swarmmanager.api;

import com.swarmmanager.auth.config.JWTSigningKeyResolver;
import com.swarmmanager.auth.mongo.Token;
import com.swarmmanager.auth.mongo.TokenRepository;
import com.swarmmanager.auth.mongo.UserRepository;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final String TOKEN_PREFIX = "Bearer";

    @Autowired
    UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @RequestMapping(method = RequestMethod.POST, value = "logout", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void logout(@RequestHeader(name = "Authorization") String tokenString) {

        Date tokenDate = Jwts.parser()
                .setSigningKeyResolver(new JWTSigningKeyResolver(userRepository))
                .parseClaimsJws(tokenString.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getExpiration();

        Token token = new Token(tokenString, tokenDate);
        tokenRepository.insert(token);
    }

}
