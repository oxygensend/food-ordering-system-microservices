package food.domain.exception;

import food.domain.exception.code.FoodErrorCode;
import food.infrastructure.exception.FoodApiException;

public class CategoryNotFoundException extends FoodApiException {
    public CategoryNotFoundException(String message) {
        super(message, FoodErrorCode.CATEGORY_NOT_FOUND);
    }
}
