package food.domain.query.restaurant.get;

import commons.cqrs.query.QueryHandler;
import food.application.response.GetRestaurantResponse;
import food.domain.entity.Category;
import food.domain.entity.Restaurant;
import food.domain.exception.RestaurantNotFoundException;
import food.infrastructure.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class GetRestaurantQueryHandler implements QueryHandler<GetRestaurantResponse, GetRestaurantQuery> {

    private final RestaurantRepository repository;

    @Override
    public GetRestaurantResponse handle(GetRestaurantQuery query) {
        Restaurant restaurant = repository.findById(query.restaurantId()).orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id: " + query.restaurantId() + " not found"));
        Set<String> categories = restaurant.getCategories().stream().map(Category::getName).collect(Collectors.toSet());

        return new GetRestaurantResponse(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getDescription(),
                restaurant.getOpeningTime(),
                restaurant.getClosingTime(),
                categories
        );
    }
}
