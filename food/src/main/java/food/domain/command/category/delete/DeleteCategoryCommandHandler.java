package food.domain.command.category.delete;

import commons.cqrs.command.CommandHandler;

public class DeleteCategoryCommandHandler implements CommandHandler<Void, DeleteCategoryCommand> {
    @Override
    public Void handle(DeleteCategoryCommand command) {
        return null;
    }
}
