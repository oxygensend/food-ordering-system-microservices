package food.domain.exception;

import food.domain.exception.code.FoodErrorCode;
import food.infrastructure.exception.FoodApiException;

public class RestaurantHasCoursesException extends FoodApiException {
    public RestaurantHasCoursesException(String message) {
        super(message, FoodErrorCode.RESTAURANT_HAS_COURSES);
    }
}
