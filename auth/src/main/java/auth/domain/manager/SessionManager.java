package auth.domain.manager;

import auth.application.response.AuthenticationResponse;
import auth.domain.entity.Session;
import auth.domain.entity.User;
import auth.domain.enums.TokenTypeEnum;
import auth.infrastructure.config.TokenConfiguration;
import auth.infrastructure.payload.TokenPayload;
import auth.infrastructure.payload.provider.TokenPayloadFactoryProvider;
import auth.infrastructure.repository.SessionRepository;
import auth.infrastructure.security.TokenStorage;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class SessionManager {

    private final SessionRepository sessionRepository;
    private final TokenConfiguration tokenConfiguration;
    private final TokenStorage tokenStorage;

    public SessionManager(SessionRepository sessionRepository,
                          TokenConfiguration tokenConfiguration,
                          TokenStorage tokenStorage
    ) {
        this.sessionRepository = sessionRepository;
        this.tokenConfiguration = tokenConfiguration;
        this.tokenStorage = tokenStorage;
    }

    public void startSession(int sessionId) {
        sessionRepository.deleteById(sessionId);
        sessionRepository.save(new Session(sessionId));
    }

    public Optional<Session> getSession(int sessionId){
       return sessionRepository.findById(sessionId);
    }


    public AuthenticationResponse prepareSession(User user) {

        // Generate refresh token
        var refreshTokenFactory = TokenPayloadFactoryProvider.getFactory(TokenTypeEnum.REFRESH);
        TokenPayload refreshPayload = refreshTokenFactory.createTokenPayload(
                new Date(System.currentTimeMillis() + tokenConfiguration.authExpirationMs),
                new Date(System.currentTimeMillis()),
                user
        );
        String refreshToken = tokenStorage.generateToken(refreshPayload);

        // Start session for this user
        startSession(user.getId());

        // Generate access token
        var accessTokenFactory = TokenPayloadFactoryProvider.getFactory(TokenTypeEnum.ACCESS);
        TokenPayload accessPayload = accessTokenFactory.createTokenPayload(
                new Date(System.currentTimeMillis() + tokenConfiguration.authExpirationMs),
                new Date(System.currentTimeMillis()),
                user
        );

        String accessToken = tokenStorage.generateToken(accessPayload);
        return new AuthenticationResponse(accessToken, refreshToken);
    }
}
