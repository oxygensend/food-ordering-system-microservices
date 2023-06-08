package auth.infrastructure.exception;

import auth.infrastructure.exception.code.AuthErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class TokenException extends AuthApiException {
    public TokenException(String message) {
        super(message, AuthErrorCode.TOKEN);
    }
}
