package food.domain.query.category.get;

import commons.cqrs.query.Query;

import java.util.UUID;

public record GetCategoryQuery(UUID id) implements Query {
}
