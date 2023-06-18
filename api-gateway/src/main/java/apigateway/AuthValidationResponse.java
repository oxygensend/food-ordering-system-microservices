package apigateway;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record AuthValidationResponse(
        boolean isAuthorized,

        String username,
        List<GrantedAuthority> authorities
){

}
