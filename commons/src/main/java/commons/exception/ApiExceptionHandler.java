package commons.exception;

import commons.exception.code.ApiErrorCode;
import commons.exception.response.ExceptionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ExceptionResponse(HttpStatus.BAD_REQUEST, ApiErrorCode.MALFORMED_JSON, error, ex));
    }

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<Object> handleCustomException(ApiException ex) {
        return buildResponseEntity(new ExceptionResponse(ex.getStatusCode(), ex.getServiceCode(), ex.getMessage()));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ExceptionResponse apiException = new ExceptionResponse(HttpStatus.BAD_REQUEST, "xx","Validation error");
        apiException.addValidationErrors(ex.getBindingResult().getFieldErrors());
        apiException.addValidationException(ex.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiException);
    }

    private ResponseEntity<Object> buildResponseEntity(ExceptionResponse exceptionResponse) {
        logger.info("Throwing an exception: " + exceptionResponse);
        return new ResponseEntity<>(exceptionResponse, exceptionResponse.getStatus());
    }
}
