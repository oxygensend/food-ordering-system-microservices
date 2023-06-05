package auth.infrastructure.security;

import auth.domain.enums.TokenTypeEnum;
import auth.infrastructure.config.TokenConfiguration;
import auth.infrastructure.payload.ClaimsPayload;
import auth.infrastructure.payload.TokenPayload;
import auth.infrastructure.payload.factory.TokenPayloadFactory;
import auth.infrastructure.payload.provider.TokenPayloadFactoryProvider;
import auth.infrastructure.utils.JwtExtractor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;


@Component
public class TokenStorage {

    final private JwtExtractor jwtExtractor;
    final private TokenConfiguration tokenUtils;

    public TokenStorage(JwtExtractor jwtService, TokenConfiguration tokenUtils) {
        this.jwtExtractor = jwtService;
        this.tokenUtils = tokenUtils;
    }

    public String generateToken(ClaimsPayload payload) {

        return Jwts.builder()
                .setClaims(payload.toClaims())
                .signWith(tokenUtils.getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }


    public TokenPayload validate(String token, TokenTypeEnum type) {
        Claims claims = jwtExtractor.extractAllClaims(token);
        TokenPayloadFactory factory = TokenPayloadFactoryProvider.getFactory(type);
        TokenPayload payload = factory.createTokenPayloadFromClaims(claims);

        if (payload.getType() != type) {
            throw new IllegalStateException("Invalid token");
        }

        return payload;
    }



}
