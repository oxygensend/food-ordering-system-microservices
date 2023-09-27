package food.domain.command.category.delete;

import commons.cqrs.command.CommandHandler;
import food.domain.exception.CategoryNotFoundException;
import food.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteCategoryCommandHandler implements CommandHandler<Void, DeleteCategoryCommand> {

    final private CategoryRepository categoryRepository;

    @Override
    public Void handle(DeleteCategoryCommand command) {

        var category = categoryRepository.findById(command.id()).orElseThrow(() -> new CategoryNotFoundException("Category with id: " + command.id() + " not found"));
        categoryRepository.delete(category);

        return null;
    }
}
