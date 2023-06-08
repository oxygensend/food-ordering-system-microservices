package auth.application.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @Size(min = 2)
        @Size(max = 64)
        String firstName,
        @Size(min = 2)
        @Size(max = 64)
        String lastName,
        @Email
        String email,
        @Size(min = 2)
        @Size(max = 200)
        String password
) {
}
