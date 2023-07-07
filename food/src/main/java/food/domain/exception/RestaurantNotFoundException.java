package food.domain.exception;

import food.domain.exception.code.FoodErrorCode;
import food.infrastructure.exception.FoodApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestaurantNotFoundException extends FoodApiException {

    public RestaurantNotFoundException(String message) {
        super(message, FoodErrorCode.RESTAURANT_NOT_FOUND);
    }
}
