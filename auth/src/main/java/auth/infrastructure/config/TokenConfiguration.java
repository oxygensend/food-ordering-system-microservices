package auth.infrastructure.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;

@Component
public class TokenConfiguration {
    @Value("${jwt.secretKey}")
    public  String secretKey;
    @Value("${jwt.authExpirationMs}")
    public  int authExpirationMs;
    @Value("${jwt.refreshExpirationMs}")
    public  int refreshExpirationMs;

    public Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
