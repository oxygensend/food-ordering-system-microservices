package commons.clients.auth;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(
        name = "auth",
        url = "${clients.auth.url}"
)
public interface AuthClient {

    @PostMapping("/api/auth/validate_token")
    AuthValidationResponse authenticate(@RequestHeader("Authorization") String $bearerToken);

}
