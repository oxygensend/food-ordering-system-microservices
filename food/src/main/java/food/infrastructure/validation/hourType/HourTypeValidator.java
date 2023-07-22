package food.infrastructure.validation.hourType;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class HourTypeValidator implements ConstraintValidator<HourType, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]");
    }
}
