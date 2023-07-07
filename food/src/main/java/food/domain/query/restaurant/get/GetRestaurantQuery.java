package food.domain.query.restaurant.get;

import food.application.cqrs.query.Query;

import java.util.UUID;

public record GetRestaurantQuery(UUID restaurantId) implements Query {
}
