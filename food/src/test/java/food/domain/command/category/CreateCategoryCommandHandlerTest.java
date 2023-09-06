package food.domain.command.category;

import food.application.request.category.CreateCategoryRequest;
import food.application.response.category.CategoryIdResponse;
import food.domain.command.category.create.CreateCategoryCommand;
import food.domain.command.category.create.CreateCategoryCommandHandler;
import food.domain.entity.Category;
import food.infrastructure.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryCommandHandlerTest {
    @InjectMocks
    private CreateCategoryCommandHandler handler;

    @Mock
    private CategoryRepository categoryRepository;


    @Test
    public void testShouldCreateCategory() {

        // Arrange
        var request = new CreateCategoryRequest(
                "Category Name",
                "Category Description"
        );
        var command = new CreateCategoryCommand(request);

        // Act
        CategoryIdResponse response = handler.handle(command);
        Mockito.verify(categoryRepository, Mockito.times(1)).save(Mockito.any(Category.class));

        // Assert
        assertNotNull(response);
    }
}
