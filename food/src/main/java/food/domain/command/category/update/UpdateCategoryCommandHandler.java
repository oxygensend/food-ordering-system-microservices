package food.domain.command.category.update;

import commons.cqrs.command.CommandHandler;
import food.application.response.category.CategoryIdResponse;
import food.domain.exception.CategoryNotFoundException;
import food.infrastructure.jackson.JsonNullableMapper;
import food.infrastructure.repository.CategoryRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UpdateCategoryCommandHandler implements CommandHandler<CategoryIdResponse, UpdateCategoryCommand> {

    private final CategoryRepository categoryRepository;
    private final JsonNullableMapper jsonNullableMapper;

    @Override
    public CategoryIdResponse handle(UpdateCategoryCommand command) {

        var request = command.request();
        var category = categoryRepository.findById(command.id()).orElseThrow(() -> new CategoryNotFoundException("Category with id: " + command.id() + " not found"));
        if (request.name() != null) {
            category.name(request.name());
        }

        if (request.description() != null && jsonNullableMapper.isPresent(request.description())) {
            category.description(jsonNullableMapper.unwrap(request.description()));
        }

        categoryRepository.save(category);
        return new CategoryIdResponse(category.id());
    }
}
