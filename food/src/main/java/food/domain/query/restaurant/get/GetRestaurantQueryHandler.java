package food.domain.query.restaurant.get;

import commons.cqrs.query.QueryHandler;
import food.application.response.restaurant.GetRestaurantResponse;
import food.domain.entity.Category;
import food.domain.entity.Restaurant;
import food.domain.exception.RestaurantNotFoundException;
import food.infrastructure.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class GetRestaurantQueryHandler implements QueryHandler<GetRestaurantResponse, GetRestaurantQuery> {

    private final RestaurantRepository repository;

    @Override
    public GetRestaurantResponse handle(GetRestaurantQuery query) {
        Restaurant restaurant = repository.findById(query.restaurantId()).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id: " + query.restaurantId() + " not found"));
        Set<String> categories = restaurant.categories().stream().map(Category::name).collect(Collectors.toSet());

        return new GetRestaurantResponse(
                restaurant.id(),
                restaurant.name(),
                restaurant.description(),
                restaurant.openingTime(),
                restaurant.closingTime(),
                categories
        );
    }
}
