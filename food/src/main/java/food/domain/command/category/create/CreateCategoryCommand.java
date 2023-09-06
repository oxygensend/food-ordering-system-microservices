package food.domain.command.category.create;

import commons.cqrs.command.Command;
import food.application.request.category.CreateCategoryRequest;

public record CreateCategoryCommand(CreateCategoryRequest request) implements Command {
}
