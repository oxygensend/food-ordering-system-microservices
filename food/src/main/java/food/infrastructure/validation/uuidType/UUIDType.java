package food.infrastructure.validation.uuidType;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UUIDTypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UUIDType {

    String message() default "Invalid UUID type. Should be in format 123e4567-e89b-12d3-a456-426614174000";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
