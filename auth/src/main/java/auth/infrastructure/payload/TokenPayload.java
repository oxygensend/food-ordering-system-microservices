package auth.infrastructure.payload;

import auth.domain.enums.TokenTypeEnum;

import java.util.Date;

abstract public class TokenPayload implements ClaimsPayload {

    protected TokenTypeEnum type;
    protected Date iat;
    protected Date exp;

    public TokenPayload(TokenTypeEnum type, Date iat, Date exp) {
        this.type = type;
        this.iat = iat;
        this.exp = exp;
    }

    public TokenTypeEnum getType() {
        return type;
    }

    public void setType(TokenTypeEnum type) {
        this.type = type;
    }

    public Date getIat() {
        return iat;
    }

    public void setIat(Date iat) {
        this.iat = iat;
    }

    public Date getExp() {
        return exp;
    }

    public void setExp(Date exp) {
        this.exp = exp;
    }



}
