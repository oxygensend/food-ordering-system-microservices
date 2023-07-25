package food.domain.command.restaurant;

import food.application.request.CreateRestaurantRequest;
import food.application.response.RestaurantIdResponse;
import food.domain.command.restaurant.create.CreateRestaurantCommand;
import food.domain.command.restaurant.create.CreateRestaurantCommandHandler;
import food.domain.entity.Category;
import food.infrastructure.repository.CategoryRepository;
import food.infrastructure.repository.RestaurantRepository;
import food.mother.CategoryMother;
import food.mother.RestaurantMother;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class CreateRestaurantCommandHandlerTest {
    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private CategoryRepository categoryRepository;


    @Test
    public void testShouldCreateRestaurant() {
        // Arrange
        var categoriesIds = new HashSet<UUID>();
        categoriesIds.add(UUID.randomUUID());
        categoriesIds.add(UUID.randomUUID());

        var categories = new ArrayList<Category>();
        categories.add(CategoryMother.withId(categoriesIds.iterator().next()));
        categories.add(CategoryMother.withId(categoriesIds.iterator().next()));

        var request = new CreateRestaurantRequest(
                "Restaurant Name",
                "Restaurant Description",
                "10:00",
                "22:00",
                categoriesIds
        );
        var command = new CreateRestaurantCommand(request);

        Mockito.when(categoryRepository.findAllById(any())).thenReturn(categories);

        // Act
        var handler = new CreateRestaurantCommandHandler(categoryRepository, restaurantRepository);
        RestaurantIdResponse response = handler.handle(command);

        // Assert
        assertNotNull(response);
    }

}
