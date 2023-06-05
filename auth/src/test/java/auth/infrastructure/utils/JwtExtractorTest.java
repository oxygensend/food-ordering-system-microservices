package auth.infrastructure.utils;

import auth.infrastructure.config.TokenConfiguration;
import helper.TokenHelper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Date;

import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class JwtExtractorTest {

    @Mock
    private TokenConfiguration tokenConfiguration;

    @Test
    public void testExtractAllClaims() {

        // Arrange
        Date currentDate = new Date();
        Date expDate = new Date(System.currentTimeMillis() + 3600);

        Claims expectedClaims = Jwts.claims();
        expectedClaims.setExpiration(expDate);
        expectedClaims.setIssuedAt(currentDate);
        expectedClaims.setSubject("test_subject");

        when(tokenConfiguration.getSignInKey()).thenReturn(TokenHelper.createSigningKey());

        String token = Jwts.builder()
                .setClaims(expectedClaims)
                .signWith(tokenConfiguration.getSignInKey(), SignatureAlgorithm.HS512)
                .compact();


        JwtExtractor jwtExtractor = new JwtExtractor(tokenConfiguration);

        // Act
        Claims extractedClaims = jwtExtractor.extractAllClaims(token);

        // Assert
        assertEquals(extractedClaims.getExpiration(), expectedClaims.getExpiration());
        assertEquals(extractedClaims.getIssuedAt(), expectedClaims.getIssuedAt());
        assertEquals(extractedClaims.getSubject(), expectedClaims.getSubject());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public TokenConfiguration tokenConfiguration() {
            TokenConfiguration config = new TokenConfiguration();
            config.secretKey = "mock_secret_key";
            config.authExpirationMs = 3600000;
            config.refreshExpirationMs = 86400000;
            return config;
        }
    }
}