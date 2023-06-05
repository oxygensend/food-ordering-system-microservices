package auth.domain.service;

import auth.application.request.AuthenticationRequest;
import auth.application.request.RefreshTokenRequest;
import auth.application.request.RegisterRequest;
import auth.application.response.AuthenticationResponse;
import auth.domain.entity.Session;
import auth.domain.entity.User;
import auth.domain.enums.RoleEnum;
import auth.domain.enums.TokenTypeEnum;
import auth.domain.manager.SessionManager;
import auth.infrastructure.config.TokenConfiguration;
import auth.infrastructure.payload.AccessTokenPayload;
import auth.infrastructure.payload.RefreshTokenPayload;
import auth.infrastructure.payload.TokenPayload;
import auth.infrastructure.payload.factory.AccessTokenPayloadFactory;
import auth.infrastructure.security.TokenStorage;
import auth.infrastructure.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenStorage tokenStorage;

    @Mock
    private SessionManager sessionManager;

    private AuthService authService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(
                userRepository,
                passwordEncoder,
                authenticationManager,
                tokenStorage,
                sessionManager
        );
    }

    @Test
    public void testAuthenticate_ValidCredentials() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        AuthenticationRequest request = new AuthenticationRequest(email, password);

        User user = new User(1, "John", "Doe", email, passwordEncoder.encode(password), true, RoleEnum.ADMIN);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        AuthenticationResponse response = authService.authenticate(request);

        // Assert
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(email);
        verify(sessionManager, times(1)).prepareSession(user);
    }

    @Test
    public void testAuthenticate_InvalidCredentials() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        AuthenticationRequest request = new AuthenticationRequest(email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.authenticate(request));
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail(email);
        verifyNoMoreInteractions(sessionManager);
    }

    @Test
    public void testRegister_NewUser() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        RegisterRequest request = new RegisterRequest("John", "Doe", email, password);
        AuthenticationResponse expectedResponse = new AuthenticationResponse("access_token", "refresh_token");

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0);
            savedUser.setId(1);
            return savedUser;
        });

        when(sessionManager.prepareSession(any(User.class))).thenReturn(expectedResponse);


        // Act
        AuthenticationResponse response = authService.register(request);

        // Assert
        assertEquals(response, expectedResponse);
        verify(userRepository, times(1)).findByEmail(email);
        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(any(User.class));
        verify(sessionManager, times(1)).prepareSession(any(User.class));
    }


    @Test
    public void testRegister_ExistingUser() {
        // Arrange
        String email = "test@example.com";
        String password = "password";
        RegisterRequest request = new RegisterRequest("John", "Doe", email, password);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> authService.register(request));
        verify(userRepository, times(1)).findByEmail(email);
        verifyNoMoreInteractions(passwordEncoder, userRepository, sessionManager, tokenStorage);
    }


    @Test
    public void test_RefreshToken() {

        // Arrange
        RefreshTokenRequest request = new RefreshTokenRequest("refresh_token");
        AuthenticationResponse expectedResponse = new AuthenticationResponse("access_token", "refresh_token");

        RefreshTokenPayload refreshTokenPayload = new RefreshTokenPayload(
                new Date(),
                new Date(System.currentTimeMillis() + 1000),
                1
        );
        Session session = new Session(1);

        when(tokenStorage.validate(anyString(), any(TokenTypeEnum.class))).thenReturn(refreshTokenPayload);
        when(sessionManager.getSession(anyInt())).thenReturn(Optional.of(session));
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(mock(User.class)));
        when(sessionManager.prepareSession(any(User.class))).thenReturn(expectedResponse);

        // Act
        AuthenticationResponse response =  authService.refreshToken(request);

        // Assert
        assertEquals(response, expectedResponse);
        verify(tokenStorage, times(1)).validate(anyString(), any(TokenTypeEnum.class));
        verify(sessionManager, times(1)).getSession(anyInt());
        verify(sessionManager, times(1)).prepareSession(any(User.class));

    }


}
