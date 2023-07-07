package food.infrastructure.exception;

import commons.exception.ApiException;

public class FoodApiException extends ApiException {
    public FoodApiException(String message, String serviceCode) {
        super(message, serviceCode);
    }
}
