package food.domain.command.restaurant.create;

import commons.cqrs.command.Command;
import food.application.request.restaurant.CreateRestaurantRequest;

public record CreateRestaurantCommand(CreateRestaurantRequest request) implements Command {

}
