package auth.infrastructure.payload;

import auth.domain.enums.TokenTypeEnum;
import auth.domain.enums.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.Date;

public class AccessTokenPayload extends TokenPayload {

    private String firstname;
    private String lastname;
    private String username;
    private RoleEnum role;

    public AccessTokenPayload( Date iat, Date exp, String firstName, String fullName, String username, RoleEnum role) {
        super(TokenTypeEnum.ACCESS, iat, exp);
        this.firstname = firstName;
        this.lastname = fullName;
        this.username = username;
        this.role = role;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public Claims toClaims() {
        Claims claims = Jwts.claims();
        claims.setSubject(username);
        claims.setIssuedAt(iat);
        claims.setExpiration(exp);
        claims.put("type", type);
        claims.put("firstname", firstname);
        claims.put("lastname", lastname);
        claims.put("role", role);

        return claims;
    }


}
