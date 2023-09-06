package food.application.response.restaurant;

import java.util.UUID;

public record RestaurantResponse(UUID id,
                                 String name,
                                 String description,
                                 String openingTime,
                                 String closingTime
) {
}