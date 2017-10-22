package com.swarmmanager.auth.util;

import com.swarmmanager.auth.config.JWTSigningKeyResolver;
import com.swarmmanager.auth.mongo.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.swarmmanager.util.DateConverter.dateToLocalDateTime;

@Component
public class TokenExtractor {

    private final String TOKEN_PREFIX = "Bearer";

    @Autowired
    private UserRepository userRepository;

    private Claims getTokenContent(String tokenString) {
        return Jwts.parser()
                .setSigningKeyResolver(new JWTSigningKeyResolver(userRepository))
                .parseClaimsJws(tokenString.replace(TOKEN_PREFIX, ""))
                .getBody();
    }

    public String getUser(String tokenString) {
        return getTokenContent(tokenString).getSubject();
    }

    public LocalDateTime getExpirationDate(String tokenString) {
        return dateToLocalDateTime(getTokenContent(tokenString).getExpiration());
    }
}
