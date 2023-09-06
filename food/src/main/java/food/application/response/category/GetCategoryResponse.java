package food.application.response.category;

import food.application.response.restaurant.RestaurantResponse;

import java.util.Set;
import java.util.UUID;

public record GetCategoryResponse(UUID id, String name, String description, Set<RestaurantResponse> restaurants) {
}
