package food.domain.command.restaurant.delete;

import commons.cqrs.command.Command;

import java.util.UUID;

public record DeleteRestaurantCommand(UUID restaurantId) implements Command {
}
