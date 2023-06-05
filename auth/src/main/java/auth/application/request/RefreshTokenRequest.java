package auth.application.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(@NotBlank String token) {}

