package food.domain.command.category.update;

import commons.cqrs.command.Command;
import food.application.request.category.UpdateCategoryRequest;

import java.util.UUID;

public record UpdateCategoryCommand(UpdateCategoryRequest request, UUID id) implements Command {
}
