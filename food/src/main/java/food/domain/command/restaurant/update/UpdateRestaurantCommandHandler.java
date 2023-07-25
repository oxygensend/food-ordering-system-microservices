package food.domain.command.restaurant.update;

import commons.cqrs.command.CommandHandler;
import food.domain.entity.Category;
import food.domain.entity.Restaurant;
import food.domain.exception.CategoriesDoesntExistException;
import food.domain.exception.RestaurantNotFoundException;
import food.infrastructure.jackson.JsonNullableMapper;
import food.infrastructure.repository.CategoryRepository;
import food.infrastructure.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@RequiredArgsConstructor
@Component
public class UpdateRestaurantCommandHandler implements CommandHandler<Void, UpdateRestaurantCommand> {

    private final RestaurantRepository restaurantRepository;
    private final CategoryRepository categoryRepository;
    private final JsonNullableMapper jsonNullableMapper;

    @Override
    public Void handle(UpdateRestaurantCommand command) {
        var request = command.request();
        Restaurant restaurant = restaurantRepository.findById(command.restaurantId()).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id: " + command.restaurantId() + " not found"));


        if (request.name() != null) {
            restaurant.name(request.name());
        }

        if (request.description() != null && jsonNullableMapper.isPresent(request.description())) {
            restaurant.description(jsonNullableMapper.unwrap(request.description()));
        }

        if (request.openingTime() != null) {
            restaurant.openingTime(request.openingTime());
        }

        if (request.closingTime() != null) {
            restaurant.closingTime(request.closingTime());
        }

        if (request.categories() != null) {
            List<Category> categories = categoryRepository.findAllById(request.categories());

            if (categories.size() != request.categories().size()) {
                var requestedCategories = request.categories();
                categories.forEach(category -> {
                    requestedCategories.remove(category.id());
                });
                throw new CategoriesDoesntExistException("Categories with ids: " + requestedCategories + " doesn't exist");
            }

            restaurant.categories(new HashSet<>(categories));
        }

        restaurantRepository.save(restaurant);
        return null;
    }
}
