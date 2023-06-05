package auth.infrastructure.utils;

import auth.infrastructure.config.TokenConfiguration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;


@Service
public class JwtExtractor {

    final private TokenConfiguration tokenUtils;

    public JwtExtractor(TokenConfiguration tokenUtils) {
        this.tokenUtils = tokenUtils;
    }

    public Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(tokenUtils.getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


}
