package auth.infrastructure.payload.factory;


import auth.infrastructure.payload.AccessTokenPayload;
import auth.infrastructure.payload.TokenPayload;
import auth.domain.enums.RoleEnum;
import auth.domain.entity.User;
import io.jsonwebtoken.Claims;

import java.util.Date;

public class AccessTokenPayloadFactory implements TokenPayloadFactory {

    @Override
    public TokenPayload createTokenPayload(Date exp, Date iat, User user) {
        return new AccessTokenPayload(
                iat,
                exp,
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole()
        );
    }

    @Override
    public TokenPayload createTokenPayloadFromClaims(Claims claims) {
        return new AccessTokenPayload(
                claims.getIssuedAt(),
                claims.getExpiration(),
                (String) claims.get("firstName"),
                (String) claims.get("lastname"),
                claims.getSubject(),
                RoleEnum.valueOf((String) claims.get("role"))
        );
    }
}
