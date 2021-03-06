package co.uk.swarmbit.api;

import co.uk.swarmbit.repository.model.Token;
import co.uk.swarmbit.repository.TokenRepository;
import co.uk.swarmbit.auth.util.TokenExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenRepository tokenRepository;

    private final TokenExtractor tokenExtractor;

    @Autowired
    public AuthController(TokenRepository tokenRepository, TokenExtractor tokenExtractor) {
        this.tokenRepository = tokenRepository;
        this.tokenExtractor = tokenExtractor;
    }

    @RequestMapping(method = RequestMethod.POST, value = "logout", produces = {MediaType.APPLICATION_JSON_VALUE})
    public void logout(@RequestHeader(name = "Authorization") String tokenString) {
        Token token = new Token(tokenString, tokenExtractor.getExpirationDate(tokenString));
        tokenRepository.insert(token);
    }

}
