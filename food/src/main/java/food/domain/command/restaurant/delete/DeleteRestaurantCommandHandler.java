package food.domain.command.restaurant.delete;

import commons.cqrs.command.CommandHandler;
import food.domain.entity.Restaurant;
import food.domain.exception.RestaurantHasCoursesException;
import food.domain.exception.RestaurantNotFoundException;
import food.infrastructure.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteRestaurantCommandHandler implements CommandHandler<Void, DeleteRestaurantCommand> {

    private final RestaurantRepository repository;


    @Override
    public Void handle(DeleteRestaurantCommand command) {

        Restaurant restaurant = repository.findById(command.restaurantId()).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id: " + command.restaurantId() + " not found"));
        if (!restaurant.courses().isEmpty()) {
            throw new RestaurantHasCoursesException("Restaurant with id: " + command.restaurantId() + " has courses");
        }

        repository.softDelete(restaurant.id());

        return null;
    }
}
