package auth.domain.manager;

import auth.application.response.AuthenticationResponse;
import auth.domain.entity.Session;
import auth.domain.entity.User;
import auth.domain.enums.RoleEnum;
import auth.domain.enums.TokenTypeEnum;
import auth.infrastructure.config.TokenConfiguration;
import auth.infrastructure.payload.TokenPayload;
import auth.infrastructure.payload.factory.TokenPayloadFactory;
import auth.infrastructure.payload.provider.TokenPayloadFactoryProvider;
import auth.infrastructure.repository.SessionRepository;
import auth.infrastructure.security.TokenStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SessionManagerTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private TokenStorage tokenStorage;

    @Mock
    private TokenConfiguration tokenConfiguration;

    private SessionManager sessionManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        sessionManager = new SessionManager(sessionRepository, tokenConfiguration, tokenStorage);
    }

    @Test
    public void testStartSession_DeleteByIdAndSave() {
        // Arrange
        int sessionId = 1;

        // Act
        sessionManager.startSession(sessionId);

        // Assert
        verify(sessionRepository, times(1)).deleteById(sessionId);
        verify(sessionRepository, times(1)).save(any(Session.class));
    }

    @Test
    public void testGetSession_Found() {
        // Arrange
        int sessionId = 1;
        Session session = new Session(sessionId);

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.of(session));

        // Act
        Optional<Session> result = sessionManager.getSession(sessionId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(session, result.get());
        verify(sessionRepository, times(1)).findById(sessionId);
    }

    @Test
    public void testGetSession_NotFound() {
        // Arrange
        int sessionId = 1;

        when(sessionRepository.findById(sessionId)).thenReturn(Optional.empty());

        // Act
        Optional<Session> result = sessionManager.getSession(sessionId);

        // Assert
        assertFalse(result.isPresent());
        verify(sessionRepository, times(1)).findById(sessionId);
    }


    @Test
    public void testPrepareSession() {
        // Arrange
        User user = new User(1, "John", "Doe", "john@doe.pl", "password", true, RoleEnum.ADMIN);

        TokenPayloadFactory refreshTokenFactory = mock(TokenPayloadFactory.class);
        TokenPayloadFactory accessTokenFactory = mock(TokenPayloadFactory.class);
        TokenPayload refreshPayload = mock(TokenPayload.class);
        TokenPayload accessPayload = mock(TokenPayload.class);

        String expectedRefreshToken = "valid_refresh_token";
        String expectedAccessToken = "valid_access_token";


        mockStatic(TokenPayloadFactoryProvider.class);
        when(TokenPayloadFactoryProvider.getFactory(TokenTypeEnum.REFRESH)).thenReturn(refreshTokenFactory);
        when(TokenPayloadFactoryProvider.getFactory(TokenTypeEnum.ACCESS)).thenReturn(accessTokenFactory);
        when(refreshTokenFactory.createTokenPayload(any(Date.class), any(Date.class), eq(user))).thenReturn(refreshPayload);
        when(accessTokenFactory.createTokenPayload(any(Date.class), any(Date.class), eq(user))).thenReturn(accessPayload);
        when(tokenStorage.generateToken(refreshPayload)).thenReturn(expectedRefreshToken);
        when(tokenStorage.generateToken(accessPayload)).thenReturn(expectedAccessToken);

        // Act
        AuthenticationResponse response = sessionManager.prepareSession(user);

        // Assert
        assertEquals(expectedAccessToken, response.accessToken());
        assertEquals(expectedRefreshToken, response.refreshToken());
        verify(sessionRepository, times(1)).deleteById(anyInt());
        verify(sessionRepository, times(1)).save(any(Session.class));
        verify(refreshTokenFactory, times(1)).createTokenPayload(any(Date.class), any(Date.class), eq(user));
        verify(accessTokenFactory, times(1)).createTokenPayload(any(Date.class), any(Date.class), eq(user));
        verify(tokenStorage, times(1)).generateToken(refreshPayload);
        verify(tokenStorage, times(1)).generateToken(accessPayload);
    }
}
