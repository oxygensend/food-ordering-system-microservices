package commons.exception.response;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExceptionResponse {
    private String code;
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;

    private List<SubExceptionResponse> subExceptions;

    private ExceptionResponse() {
        this.timestamp = LocalDateTime.now();
    }

    public ExceptionResponse(HttpStatus status, String code) {
        this();
        this.status = status;
        this.code = code;
    }

    public ExceptionResponse(HttpStatus status, String code, String message) {
        this();
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ExceptionResponse(HttpStatus status, String code, Throwable exception) {
        this();
        this.status = status;
        this.code = code;
        this.message = "Unexpected error";
        this.debugMessage = exception.getLocalizedMessage();
    }

    public ExceptionResponse(HttpStatus status, String code, String message, Throwable exception) {
        this();
        this.status = status;
        this.code = code;
        this.message = message;
        this.debugMessage = exception.getLocalizedMessage();
    }
    private void addSubException(SubExceptionResponse subError) {
        if (subExceptions == null) {
            subExceptions = new ArrayList<>();
        }
        subExceptions.add(subError);
    }

    private void addValidationException(String object, String field, Object rejectedValue, String message) {
        addSubException(new ValidationExceptionResponse(object, field, rejectedValue, message));
    }

    private void addValidationException(String object, String message) {
        addSubException(new ValidationExceptionResponse(object, message));
    }

    private void addValidationException(FieldError fieldError) {
        this.addValidationException(
                fieldError.getObjectName(),
                fieldError.getField(),
                fieldError.getRejectedValue(),
                fieldError.getDefaultMessage());
    }

    private void addValidationException(ObjectError objectError) {
        this.addValidationException(
                objectError.getObjectName(),
                objectError.getDefaultMessage());
    }

    public void addValidationException(List<ObjectError> globalErrors) {
        globalErrors.forEach(this::addValidationException);
    }


    public void addValidationErrors(List<FieldError> fieldErrors) {
        fieldErrors.forEach(this::addValidationException);
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }
}
