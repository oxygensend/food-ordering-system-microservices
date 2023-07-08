package food.domain.query.restaurant.get;

import commons.cqrs.query.Query;

import java.util.UUID;

public record GetRestaurantQuery(UUID restaurantId) implements Query {
}
