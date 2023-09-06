package food.application.request.restaurant;

import food.infrastructure.validation.hourType.HourType;
import org.openapitools.jackson.nullable.JsonNullable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public record UpdateRestaurantRequest(
        String name,
        JsonNullable<String> description,
        @HourType
        String openingTime,
        @HourType
        String closingTime,
        Set<UUID> categories
) {
    public UpdateRestaurantRequest() {
        this(null, JsonNullable.undefined(), null, null, new HashSet<>());
    }

    public UpdateRestaurantRequest(String name, String description,
                                   String openingTime,
                                   String closingTime, Set<UUID> categories) {
        this(name, JsonNullable.of(description), openingTime, closingTime, categories);
    }
}
