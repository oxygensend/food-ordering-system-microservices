package auth.infrastructure.payload;

import io.jsonwebtoken.Claims;

public interface ClaimsPayload {
    Claims toClaims();
}
