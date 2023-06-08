package auth.domain.exception;

import auth.domain.exception.code.AuthErrorCode;
import auth.infrastructure.exception.AuthApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends AuthApiException {
    public UserNotFoundException(String message) {
        super(message, AuthErrorCode.USER_NOT_FOUND);
    }
}
