package food.domain.command.category;

import food.domain.command.category.delete.DeleteCategoryCommand;
import food.domain.command.category.delete.DeleteCategoryCommandHandler;
import food.domain.exception.CategoryNotFoundException;
import food.infrastructure.repository.CategoryRepository;
import food.mother.CategoryMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryCommandHandlerTest {

    @InjectMocks
    private DeleteCategoryCommandHandler handler;

    @Mock
    private CategoryRepository categoryRepository;


    @Test
    public void testShouldDeleteCategory() {
        //Arrange
        var id = UUID.randomUUID();
        var command = new DeleteCategoryCommand(id);
        var category = CategoryMother.withId(id);
        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.of(category));

        //Act
        handler.handle(command);

        //Assert
        Mockito.verify(categoryRepository).delete(category);
    }

    @Test
    public void testShouldThrowExceptionWhenCategoryDoesNotExist() {
        //Arrange
        var id = UUID.randomUUID();
        var command = new DeleteCategoryCommand(id);
        Mockito.when(categoryRepository.findById(id)).thenReturn(Optional.empty());

        //Act && Assert
        assertThrows(CategoryNotFoundException.class, () -> handler.handle(command));
    }
}
