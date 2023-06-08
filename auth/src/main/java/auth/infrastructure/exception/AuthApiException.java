package auth.infrastructure.exception;

import commons.exception.ApiException;

public abstract class AuthApiException extends ApiException {
    public AuthApiException(String message, String code) {
        super(message, code);
    }
}
