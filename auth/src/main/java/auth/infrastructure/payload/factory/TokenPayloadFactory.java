package auth.infrastructure.payload.factory;

import auth.domain.enums.TokenTypeEnum;
import auth.infrastructure.payload.TokenPayload;
import auth.domain.entity.User;
import io.jsonwebtoken.Claims;

import java.util.Date;


public interface TokenPayloadFactory {

    TokenPayload createTokenPayload(Date exp, Date iat, User user);

    TokenPayload createTokenPayloadFromClaims(Claims claims);
}
