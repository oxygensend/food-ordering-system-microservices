package auth.infrastructure.payload;

import auth.domain.entity.User;
import auth.infrastructure.payload.factory.RefreshTokenPayloadFactory;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RefreshTokenPayloadFactoryTest {

    private RefreshTokenPayloadFactory factory;

    @BeforeEach
    public void setup() {
        factory = new RefreshTokenPayloadFactory();
    }

    @Test
    public void testCreateTokenPayload() {
        // Arrange
        Date exp = new Date();
        Date iat = new Date();
        User user = mock(User.class);
        when(user.getId()).thenReturn(1);


        // Act
        RefreshTokenPayload payload = (RefreshTokenPayload) factory.createTokenPayload(exp, iat, user);

        // Assert
        assertEquals(RefreshTokenPayload.class, payload.getClass());
        assertEquals(iat, payload.getIat());
        assertEquals(exp, payload.getExp());
        assertEquals(1,  payload.getSessionId());
    }

    @Test
    public void testCreateTokenPayloadFromClaims() {
        // Arrange
        Date issuedAt = new Date();
        Date expiration = new Date();
        Claims claims = mock(Claims.class);
        when(claims.getIssuedAt()).thenReturn(issuedAt);
        when(claims.getExpiration()).thenReturn(expiration);
        when(claims.getSubject()).thenReturn("1");


        // Act
        RefreshTokenPayload payload = (RefreshTokenPayload) factory.createTokenPayloadFromClaims(claims);

        // Assert
        assertEquals(RefreshTokenPayload.class, payload.getClass());
        assertEquals(issuedAt, payload.getIat());
        assertEquals(expiration, payload.getExp());
        assertEquals(1,  payload.getSessionId());
    }
}
