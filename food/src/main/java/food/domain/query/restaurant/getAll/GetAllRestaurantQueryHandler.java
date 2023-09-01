package food.domain.query.restaurant.getAll;

import commons.cqrs.query.QueryHandler;
import food.application.response.RestaurantPagedListResponse;
import food.application.response.RestaurantResponse;
import food.domain.specification.RestaurantSpecification;
import food.domain.entity.Restaurant;
import food.infrastructure.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class GetAllRestaurantQueryHandler implements QueryHandler<RestaurantPagedListResponse, GetAllRestaurantQuery> {


    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantPagedListResponse handle(GetAllRestaurantQuery query) {

        Specification<Restaurant> specification = RestaurantSpecification.builder()
                .name(query.name())
                .searchTerm(query.search())
                .categories(query.categories())
                .build();
        Page<Restaurant> paginator = restaurantRepository.findAll(specification, query.pageable());

        List<RestaurantResponse> data = new ArrayList<>();
        for (var restaurant : paginator.getContent()) {
            data.add(new RestaurantResponse(
                    restaurant.id(),
                    restaurant.name(),
                    restaurant.description(),
                    restaurant.openingTime(),
                    restaurant.closingTime())
            );
        }

        return new RestaurantPagedListResponse(data, paginator.getTotalElements(), paginator.getTotalPages());
    }
}
