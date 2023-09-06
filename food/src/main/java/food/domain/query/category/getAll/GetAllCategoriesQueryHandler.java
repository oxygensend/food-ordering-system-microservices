package food.domain.query.category.getAll;

import commons.cqrs.query.QueryHandler;
import food.application.response.category.CategoryPagedListResponse;

public class GetAllCategoriesQueryHandler implements QueryHandler<CategoryPagedListResponse, GetAllCategoriesQuery> {
    @Override
    public CategoryPagedListResponse handle(GetAllCategoriesQuery query) {
        return null;
    }
}
