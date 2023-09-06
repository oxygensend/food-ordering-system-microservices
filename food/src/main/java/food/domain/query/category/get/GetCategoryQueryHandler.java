package food.domain.query.category.get;

import commons.cqrs.query.QueryHandler;
import food.application.response.category.GetCategoryResponse;
import food.application.response.restaurant.RestaurantResponse;
import food.domain.exception.CategoryNotFoundException;
import food.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class GetCategoryQueryHandler implements QueryHandler<GetCategoryResponse, GetCategoryQuery> {

    private final CategoryRepository repository;

    @Override
    public GetCategoryResponse handle(GetCategoryQuery query) {
        var category = repository.findById(query.id()).orElseThrow(() -> new CategoryNotFoundException("Category with id: " + query.id() + " not found"));
        var restaurants = category.restaurants().stream().map(restaurant -> new RestaurantResponse(
                restaurant.id(),
                restaurant.name(),
                restaurant.description(),
                restaurant.openingTime(),
                restaurant.closingTime()
        )).collect(Collectors.toSet());

        return new GetCategoryResponse(
                category.id(),
                category.name(),
                category.description(),
                restaurants
        );
    }
}
