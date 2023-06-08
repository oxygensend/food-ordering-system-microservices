package auth.domain.exception;

import auth.domain.exception.code.AuthErrorCode;
import auth.infrastructure.exception.AuthApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyExistsException extends AuthApiException {
    public UserAlreadyExistsException() {
        super("User with this email already exists", AuthErrorCode.USER_ALREADY_EXISTS);
    }

    public UserAlreadyExistsException(String message) {
        super(message, AuthErrorCode.USER_ALREADY_EXISTS);
    }
}
