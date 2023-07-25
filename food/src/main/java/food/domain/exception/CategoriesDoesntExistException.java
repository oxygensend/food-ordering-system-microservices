package food.domain.exception;

import food.domain.exception.code.FoodErrorCode;
import food.infrastructure.exception.FoodApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CategoriesDoesntExistException extends FoodApiException {
    public CategoriesDoesntExistException(String message) {
        super(message, FoodErrorCode.CATEGORIES_DOESNT_EXIST);
    }
}
