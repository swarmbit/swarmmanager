package com.swarmmanager.auth.config;

import com.swarmmanager.repository.TokenRepository;
import com.swarmmanager.repository.User;
import com.swarmmanager.repository.UserRepository;
import com.swarmmanager.auth.util.TokenExtractor;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;

import static java.util.Collections.emptyList;

@Service
public class TokenAuthenticationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationService.class.getName());

    static final String ALGORITHM = "RSA";
    // 10 days
    private static final long EXPIRATION_TIME = 864_000_000;
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private TokenExtractor tokenExtractor;

    void addAuthentication(HttpServletResponse res, String username)  {
        User user = userRepository.findByUsername(username);
        byte [][] secret = user.getSecret();
        if (secret == null) {
            secret = generateKeys();
            if (secret == null) {
                return;
            }
            user.setSecret(secret);
            userRepository.save(user);
        }
        String JWT;
        try {
            JWT = Jwts.builder()
                    .setSubject(username)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.RS256, KeyFactory.getInstance(ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(secret[0])))
                    .compact();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.error("Problem generating new secret", e);
            return;
        }
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }

    Authentication getAuthentication(HttpServletRequest request) {

        String tokenString = request.getHeader(HEADER_STRING);
        if (tokenString != null) {

            if (tokenRepository.findFirstByToken(tokenString) != null){
                return null;
            }

            try {
                String user = tokenExtractor.getUser(tokenString);
                return user != null ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
            } catch (ExpiredJwtException e) {
                return null;
            } catch (IllegalArgumentException e) {
                if (!StringUtils.equals(e.getMessage(), "A signing key must be specified if the specified JWT is digitally signed.")) {
                    throw e;
                }
                return null;
            }
        }
        return null;
    }

    private byte[][] generateKeys() {
            try {
                KeyPairGenerator keyGenerator = KeyPairGenerator.getInstance(ALGORITHM);
                keyGenerator.initialize(1024);
                KeyPair kp = keyGenerator.genKeyPair();
                byte [][] secret = new byte[2][];
                secret[0] = kp.getPrivate().getEncoded();
                secret[1] = kp.getPublic().getEncoded();
                return secret;
            } catch (NoSuchAlgorithmException e) {
                LOGGER.error("Problem generating new secret", e);
                return null;
            }
    }
}
