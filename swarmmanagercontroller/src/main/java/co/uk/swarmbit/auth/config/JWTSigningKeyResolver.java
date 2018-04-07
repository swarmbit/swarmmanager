package co.uk.swarmbit.auth.config;

import co.uk.swarmbit.repository.UserRepository;
import co.uk.swarmbit.repository.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class JWTSigningKeyResolver implements SigningKeyResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTSigningKeyResolver.class.getName());

    private UserRepository userRepository;

    public JWTSigningKeyResolver(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, Claims claims) {
        User user = userRepository.findByUsername(claims.getSubject());
        return getKey(user);
    }

    @Override
    public Key resolveSigningKey(JwsHeader jwsHeader, String s) {
        User user = userRepository.findByUsername(s);
        return getKey(user);
    }

    private Key getKey(User user) {
        if (user != null && user.getSecret() != null) {
            try {
                return KeyFactory.getInstance(TokenAuthenticationService.ALGORITHM).generatePublic(new X509EncodedKeySpec(user.getSecret()[1]));
            } catch(NoSuchAlgorithmException | InvalidKeySpecException e){
                LOGGER.error("Problem reading secret", e);
            }
        }
        return null;
    }
}
