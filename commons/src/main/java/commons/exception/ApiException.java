package commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public abstract class ApiException  extends RuntimeException {
    private final String serviceCode;

    public ApiException(String message, String serviceCode) {
        super(message);
        this.serviceCode = serviceCode;
    }
    public HttpStatus getStatusCode() {
        ResponseStatus responseStatus = this.getClass().getAnnotation(ResponseStatus.class);
        return responseStatus.value();
    }

    public String getServiceCode() {
        return serviceCode;
    }
}