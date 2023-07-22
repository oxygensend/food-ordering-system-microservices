package food.infrastructure.validation.hourType;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = HourTypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HourType {

    String message() default "Invalid hour type. Should be in format HH:MM";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
