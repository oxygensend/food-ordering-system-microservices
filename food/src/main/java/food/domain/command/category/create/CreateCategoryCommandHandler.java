package food.domain.command.category.create;

import commons.cqrs.command.CommandHandler;
import food.application.response.category.CategoryIdResponse;
import food.domain.entity.Category;
import food.infrastructure.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CreateCategoryCommandHandler implements CommandHandler<CategoryIdResponse, CreateCategoryCommand> {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryIdResponse handle(CreateCategoryCommand command) {

        var request = command.request();
        var category = Category.builder()
                .name(request.name())
                .description(request.description())
                .build();
        categoryRepository.save(category);

        return new CategoryIdResponse(category.id());
    }
}
