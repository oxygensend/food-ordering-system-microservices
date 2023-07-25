package food.domain.command.restaurant.update;

import commons.cqrs.command.Command;
import food.application.request.UpdateRestaurantRequest;

import java.util.UUID;

public record UpdateRestaurantCommand(UpdateRestaurantRequest request, UUID restaurantId) implements Command {
}
