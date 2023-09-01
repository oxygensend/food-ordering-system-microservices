package food.domain.query.restaurant.getAll;

import commons.cqrs.query.Query;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public record GetAllRestaurantQuery(
        Pageable pageable,
        String name,
        String search,
        List<UUID> categories
) implements Query {
}
