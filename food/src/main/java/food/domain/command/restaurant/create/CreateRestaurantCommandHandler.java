package food.domain.command.restaurant.create;

import commons.cqrs.command.CommandHandler;
import food.application.response.RestaurantIdResponse;
import food.domain.entity.Restaurant;
import food.infrastructure.repository.CategoryRepository;
import food.infrastructure.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CreateRestaurantCommandHandler implements CommandHandler<RestaurantIdResponse, CreateRestaurantCommand> {

    private final CategoryRepository categoryRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantIdResponse handle(CreateRestaurantCommand command) {
        var request = command.request();
        var categories = new HashSet<>(categoryRepository.findAllById(request.categories()));
        var restaurant = Restaurant.builder()
                .name(request.name())
                .description(request.description())
                .openingTime(request.openingTime())
                .closingTime(request.closingTime())
                .categories(categories)
                .build();

        restaurantRepository.save(restaurant);

        return new RestaurantIdResponse(restaurant.getId());
    }
}
