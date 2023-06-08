package commons.exception.response;

public class ValidationExceptionResponse implements SubExceptionResponse {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ValidationExceptionResponse(String object, String message) {
        this.object = object;
        this.message = message;
    }

    public ValidationExceptionResponse(String object, String field, Object rejectedValue, String message) {
        this.object = object;
        this.field = field;
        this.rejectedValue = rejectedValue;
        this.message = message;
    }

    public String getObject() {
        return object;
    }

    public String getField() {
        return field;
    }

    public Object getRejectedValue() {
        return rejectedValue;
    }

    public String getMessage() {
        return message;
    }
}