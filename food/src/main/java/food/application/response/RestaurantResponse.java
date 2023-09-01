package food.application.response;

import java.util.UUID;

public record RestaurantResponse(UUID id,
                                 String name,
                                 String description,
                                 String openingTime,
                                 String closingTime
) {
}