package auth.infrastructure.payload;

import auth.domain.enums.TokenTypeEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class RefreshTokenPayload extends TokenPayload {

    public int sessionId;

    public RefreshTokenPayload( Date iat, Date exp, int sessionId) {
        super(TokenTypeEnum.REFRESH, iat, exp);
        this.sessionId = sessionId;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
    }

    public Claims toClaims() {
        Claims claims = Jwts.claims();
        claims.setSubject(String.valueOf(sessionId));
        claims.setIssuedAt(iat);
        claims.setExpiration(exp);
        claims.put("type", type);

        return claims;
    }
}
