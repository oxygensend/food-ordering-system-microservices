package food.domain.command.category.delete;

import commons.cqrs.command.Command;

import java.util.UUID;

public record DeleteCategoryCommand(UUID id) implements Command {
}
