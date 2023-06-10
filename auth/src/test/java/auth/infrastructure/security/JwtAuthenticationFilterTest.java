package auth.infrastructure.security;

import auth.domain.entity.User;
import auth.domain.enums.TokenTypeEnum;
import auth.infrastructure.payload.AccessTokenPayload;
import auth.infrastructure.payload.factory.AccessTokenPayloadFactory;
import auth.infrastructure.payload.provider.TokenPayloadFactoryProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Date;

import static org.mockito.Mockito.*;

public class JwtAuthenticationFilterTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private TokenStorage tokenStorage;

    @Mock
    private SecurityContext securityContext;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private AccessTokenPayloadFactory accessTokenPayloadFactory;

    private User user;

    @Mock
    private HttpServletRequest httpServletRequest;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        jwtAuthenticationFilter = new JwtAuthenticationFilter(userDetailsService, tokenStorage, httpServletRequest);
        SecurityContextHolder.setContext(securityContext);

        accessTokenPayloadFactory = (AccessTokenPayloadFactory) TokenPayloadFactoryProvider.getFactory(TokenTypeEnum.ACCESS);

        user = new User("firstName", "lastName", "test@test.com", "test123", true);
    }

    @Test
    public void testDoFilterInternal_NoAuthorizationHeader() throws ServletException, java.io.IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(tokenStorage, userDetailsService, securityContext);
    }

    @Test
    public void testDoFilterInternal_InvalidAuthorizationHeader() throws ServletException, java.io.IOException {
        // Arrange
        when(request.getHeader("Authorization")).thenReturn("Invalid");

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(tokenStorage, userDetailsService, securityContext);
    }

    @Test
    public void testDoFilterInternal_ValidAuthorizationHeader_ExpiredToken() throws ServletException, java.io.IOException {

        // Arrange
        String jwtToken = "valid_token";
        AccessTokenPayload tokenPayload = (AccessTokenPayload) accessTokenPayloadFactory.createTokenPayload(
                new Date(),
                new Date(),
                user
        );

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(tokenStorage.validate(jwtToken, TokenTypeEnum.ACCESS)).thenReturn(tokenPayload);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(tokenStorage).validate(jwtToken, TokenTypeEnum.ACCESS);
        verify(filterChain).doFilter(request, response);
        verifyNoInteractions(userDetailsService);
    }

    @Test
    public void testDoFilterInternal_ValidAuthorizationHeader_ValidToken() throws ServletException, java.io.IOException {
        // Arrange
        String jwtToken = "valid_token";
        AccessTokenPayload tokenPayload = (AccessTokenPayload) accessTokenPayloadFactory.createTokenPayload(
                new Date(System.currentTimeMillis() + 1000),
                new Date(),
                user
        );

        UserDetails userDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);

        when(request.getHeader("Authorization")).thenReturn("Bearer " + jwtToken);
        when(tokenStorage.validate(jwtToken, TokenTypeEnum.ACCESS)).thenReturn(tokenPayload);
        when(userDetailsService.loadUserByUsername(tokenPayload.getUsername())).thenReturn(userDetails);
        when(userDetails.getAuthorities()).thenReturn(null);
        when(userDetails.getUsername()).thenReturn(tokenPayload.getUsername());
        when(securityContext.getAuthentication()).thenReturn(null);
        when(authentication.getDetails()).thenReturn(null);
        doNothing().when(securityContext).setAuthentication(any(Authentication.class));

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(tokenStorage).validate(jwtToken, TokenTypeEnum.ACCESS);
        verify(userDetailsService).loadUserByUsername(tokenPayload.getUsername());
        verify(userDetails).getAuthorities();
        verify(securityContext).getAuthentication();
        verify(securityContext).setAuthentication(any(Authentication.class));
        verify(filterChain).doFilter(request, response);
    }

}
