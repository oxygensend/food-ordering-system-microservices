package apigateway.auth;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

public record AuthValidationResponse(
        boolean isAuthorized,

        String username,
        @JsonDeserialize(using = GrantedAuthorityDeserializer.class)
        List<GrantedAuthority> authorities
){

}
