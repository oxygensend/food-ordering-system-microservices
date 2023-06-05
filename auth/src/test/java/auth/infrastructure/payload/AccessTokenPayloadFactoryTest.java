package auth.infrastructure.payload;

import auth.domain.entity.User;
import auth.domain.enums.RoleEnum;
import auth.infrastructure.payload.factory.AccessTokenPayloadFactory;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class AccessTokenPayloadFactoryTest {

    private AccessTokenPayloadFactory factory;

    @BeforeEach
    public void setup() {
        factory = new AccessTokenPayloadFactory();
    }

    @Test
    public void testCreateTokenPayload_ValidArguments() {
        // Arrange
        Date expDate = new Date(System.currentTimeMillis() + 3600);
        Date iatDate = new Date();
        User user = new User("John", "Doe", "john.doe@example.com", "test123", RoleEnum.USER);

        // Act
        TokenPayload tokenPayload = factory.createTokenPayload(expDate, iatDate, user);

        // Assert
        assertNotNull(tokenPayload);
        assertTrue(tokenPayload instanceof AccessTokenPayload);

        AccessTokenPayload accessTokenPayload = (AccessTokenPayload) tokenPayload;
        assertEquals(iatDate, accessTokenPayload.getIat());
        assertEquals(expDate, accessTokenPayload.getExp());
        assertEquals(user.getFirstName(), accessTokenPayload.getFirstname());
        assertEquals(user.getLastName(), accessTokenPayload.getLastname());
        assertEquals(user.getEmail(), accessTokenPayload.getUsername());
        assertEquals(user.getRole(), accessTokenPayload.getRole());
    }

    @Test
    public void testCreateTokenPayloadFromClaims_ValidClaims() {
        // Arrange
        Date iatDate = new Date();
        Date expDate = new Date(System.currentTimeMillis() + 3600);
        Claims claims = createClaims(iatDate, expDate, "John", "Doe", "john.doe@example.com", RoleEnum.USER);

        // Act
        TokenPayload tokenPayload = factory.createTokenPayloadFromClaims(claims);

        // Assert
        assertNotNull(tokenPayload);
        assertTrue(tokenPayload instanceof AccessTokenPayload);

        AccessTokenPayload accessTokenPayload = (AccessTokenPayload) tokenPayload;
        assertEquals(iatDate, accessTokenPayload.getIat());
        assertEquals(expDate, accessTokenPayload.getExp());
        assertEquals("John", accessTokenPayload.getFirstname());
        assertEquals("Doe", accessTokenPayload.getLastname());
        assertEquals("john.doe@example.com", accessTokenPayload.getUsername());
        assertEquals(RoleEnum.USER, accessTokenPayload.getRole());
    }

    private Claims createClaims(Date iat, Date exp, String firstName, String lastName, String email, RoleEnum role) {
        Claims claims = mock(Claims.class);
        claims.setIssuedAt(iat);
        claims.setExpiration(exp);
        claims.put("firstName", firstName);
        claims.put("lastName", lastName);
        claims.setSubject(email);
        claims.put("role", role.toString());
        return claims;
    }
}
