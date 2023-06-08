package auth.infrastructure.exception;

import commons.exception.ApiExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class AuthExceptionHandler extends ApiExceptionHandler {

}
