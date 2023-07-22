package food.application.request;

import food.infrastructure.validation.hourType.HourType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;
import java.util.UUID;

public record CreateRestaurantRequest(
        @NotBlank
        String name,
        @NotBlank
        String description,
        @NotBlank
        @HourType
        String openingTime,
        @NotBlank
        @HourType
        String closingTime,

        @NotEmpty(message = "Categories cannot be empty")
        Set<UUID> categories


) {
}
