package auth.infrastructure.payload;

import auth.domain.enums.RoleEnum;
import auth.domain.enums.TokenTypeEnum;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TokenPayloadTest {


    @Test
    public void test_AccessTokenPayloadToClaims() {
        // Arrange
        var accessTokenPayload = new AccessTokenPayload(new Date(), new Date(), "test", "test", "test@tes.com", RoleEnum.ADMIN);

        // Act
        var claims = accessTokenPayload.toClaims();

        // Assert
        assertInstanceOf(Claims.class, accessTokenPayload.toClaims());
        assertEquals(claims.getExpiration().toString(), accessTokenPayload.getExp().toString());
        assertEquals(claims.getSubject(), accessTokenPayload.getUsername());
        assertEquals(claims.getIssuedAt().toString(), accessTokenPayload.getIat().toString());
        assertEquals(claims.get("type"), accessTokenPayload.getType());
        assertEquals(claims.get("role"), accessTokenPayload.getRole());
        assertEquals(claims.get("lastname"), accessTokenPayload.getLastname());
        assertEquals(claims.get("firstname"), accessTokenPayload.getFirstname());
    }

    @Test
    public void test_RefreshTokenPayloadToClaims() {
        // Arrange
        var accessTokenPayload = new RefreshTokenPayload(new Date(), new Date(), 1);

        // Act
        var claims = accessTokenPayload.toClaims();

        // Assert
        assertInstanceOf(Claims.class, accessTokenPayload.toClaims());
        assertEquals(claims.getExpiration().toString(), accessTokenPayload.getExp().toString());
        assertEquals(Integer.parseInt(claims.getSubject()), accessTokenPayload.getSessionId());
        assertEquals(claims.getIssuedAt().toString(), accessTokenPayload.getIat().toString());
        assertEquals(claims.get("type"), accessTokenPayload.getType());
    }
}
