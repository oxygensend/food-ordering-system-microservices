package auth.domain.exception;

import auth.domain.exception.code.AuthErrorCode;
import auth.infrastructure.exception.AuthApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UserIsUnauthorizedException extends AuthApiException {
    public UserIsUnauthorizedException() {
        super("User is not authorized", AuthErrorCode.USER_IS_UNAUTHORIZED);
    }
}
