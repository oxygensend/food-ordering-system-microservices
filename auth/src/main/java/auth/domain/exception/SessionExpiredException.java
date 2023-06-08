package auth.domain.exception;

import auth.domain.exception.code.AuthErrorCode;
import auth.infrastructure.exception.AuthApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class SessionExpiredException extends AuthApiException {
    public SessionExpiredException(String message) {
        super(message, AuthErrorCode.SESSION_EXPIRED);
    }


}
