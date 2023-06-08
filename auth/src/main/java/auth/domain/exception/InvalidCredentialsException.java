package auth.domain.exception;

import auth.domain.exception.code.AuthErrorCode;
import auth.infrastructure.exception.AuthApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidCredentialsException extends AuthApiException {
    public InvalidCredentialsException() {
        super("Invalid credentials", AuthErrorCode.INVALID_CREDENTIALS);
    }

    public InvalidCredentialsException(String message) {
        super(message, AuthErrorCode.INVALID_CREDENTIALS);
    }
}
