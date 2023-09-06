package food.domain.query.category.getAll;

import commons.cqrs.query.Query;
import org.springframework.data.domain.Pageable;

public record GetAllCategoriesQuery(Pageable pageable) implements Query {
}
