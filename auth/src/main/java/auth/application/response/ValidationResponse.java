package auth.application.response;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record ValidationResponse (
        boolean isAuthorized,

        String username,
        List<GrantedAuthority> authorities
){

}
