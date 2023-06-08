package auth.infrastructure.security;

import auth.domain.enums.TokenTypeEnum;
import auth.infrastructure.config.TokenConfiguration;
import auth.infrastructure.exception.TokenException;
import auth.infrastructure.payload.RefreshTokenPayload;
import auth.infrastructure.payload.TokenPayload;
import auth.infrastructure.payload.factory.TokenPayloadFactory;
import auth.infrastructure.utils.JwtExtractor;
import helper.TokenHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TokenStorageTest {

    @Mock
    private JwtExtractor jwtExtractor;

    @Mock
    private TokenConfiguration tokenConfiguration;

    private TokenStorage tokenStorage;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        tokenStorage = new TokenStorage(jwtExtractor, tokenConfiguration);
    }

    @Test
    public void testGenerateToken() {
        // Arrange
        Date currentDate = new Date();
        Date expDate = new Date(System.currentTimeMillis() + 3600);
        RefreshTokenPayload payload = new RefreshTokenPayload(currentDate, expDate, 1);

        when(tokenConfiguration.getSignInKey()).thenReturn(TokenHelper.createSigningKey());

        // Act
        String token = tokenStorage.generateToken(payload);

        // Assert
        assertNotNull(token);
        verify(tokenConfiguration, times(1)).getSignInKey();
    }

    @Test
    public void testValidate_ValidTokenAndType() {
        // Arrange
        String token = "valid_token";
        TokenTypeEnum type = TokenTypeEnum.REFRESH;

        Claims claims = createClaims(type);

        TokenPayloadFactory factory = mock(TokenPayloadFactory.class);
        RefreshTokenPayload payload = new RefreshTokenPayload(claims.getIssuedAt(), claims.getExpiration(), 1);

        when(jwtExtractor.extractAllClaims(token)).thenReturn(claims);
        when(factory.createTokenPayloadFromClaims(claims)).thenReturn(payload);

        // Act
        TokenPayload result = tokenStorage.validate(token, type);

        // Assert
        assertNotNull(result);
        assertEquals(type, result.getType());
        verify(jwtExtractor, times(1)).extractAllClaims(token);
    }

    @Test
    public void testValidate_InvalidToken() {
        // Arrange
        String token = "valid_token";
        TokenTypeEnum type = TokenTypeEnum.ACCESS;

        Claims claims = createClaims(type);

        TokenPayloadFactory factory = mock(TokenPayloadFactory.class);
        RefreshTokenPayload payload = new RefreshTokenPayload(claims.getIssuedAt(), claims.getExpiration(), 1);

        when(jwtExtractor.extractAllClaims(token)).thenReturn(claims);
        when(factory.createTokenPayloadFromClaims(claims)).thenReturn(payload);

        // Act & Assert
        assertThrows(TokenException.class, () -> tokenStorage.validate(token, TokenTypeEnum.REFRESH));

        // Verify
        verify(jwtExtractor, times(1)).extractAllClaims(token);
    }

    private Claims createClaims(TokenTypeEnum type) {
        Date currentDate = new Date();
        Date expDate = new Date(System.currentTimeMillis() + 3600);

        Claims claims = Jwts.claims();
        claims.setSubject("1");
        claims.setExpiration(expDate);
        claims.setIssuedAt(currentDate);
        claims.put("type", type.toString());

        return claims;
    }
}
