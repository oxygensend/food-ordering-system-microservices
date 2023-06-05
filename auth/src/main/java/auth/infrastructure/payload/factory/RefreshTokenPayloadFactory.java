package auth.infrastructure.payload.factory;

import auth.infrastructure.payload.RefreshTokenPayload;
import auth.infrastructure.payload.TokenPayload;
import auth.domain.entity.User;
import io.jsonwebtoken.Claims;

import java.util.Date;


public class RefreshTokenPayloadFactory implements TokenPayloadFactory  {
    @Override
    public TokenPayload createTokenPayload( Date exp, Date iat, User user) {
       return new RefreshTokenPayload(
               iat,
               exp,
               user.getId()
       );
    }

    @Override
    public TokenPayload createTokenPayloadFromClaims(Claims claims) {
        return new RefreshTokenPayload(
                claims.getIssuedAt(),
                claims.getExpiration(),
                Integer.parseInt(claims.getSubject())
        );
    }
}
