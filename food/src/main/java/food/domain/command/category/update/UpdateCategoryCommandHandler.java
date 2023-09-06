package food.domain.command.category.update;

import commons.cqrs.command.CommandHandler;
import food.application.response.category.CategoryIdResponse;

public class UpdateCategoryCommandHandler implements CommandHandler<CategoryIdResponse, UpdateCategoryCommand>{
    @Override
    public CategoryIdResponse handle(UpdateCategoryCommand command) {
        return null;
    }
}
