package commons.clients.auth;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "auth",
        url = "${clients.auth.url}"
)
public interface AuthClient {



}
