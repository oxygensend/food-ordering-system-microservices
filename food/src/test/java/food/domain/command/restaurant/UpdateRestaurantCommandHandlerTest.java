package food.domain.command.restaurant;

import food.application.request.UpdateRestaurantRequest;
import food.domain.command.restaurant.update.UpdateRestaurantCommand;
import food.domain.command.restaurant.update.UpdateRestaurantCommandHandler;
import food.domain.entity.Category;
import food.domain.exception.CategoriesDoesntExistException;
import food.domain.exception.RestaurantNotFoundException;
import food.infrastructure.jackson.JsonNullableMapper;
import food.infrastructure.repository.CategoryRepository;
import food.infrastructure.repository.RestaurantRepository;
import food.mother.CategoryMother;
import food.mother.RestaurantMother;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UpdateRestaurantCommandHandlerTest {

    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private JsonNullableMapper jsonNullableMapper;


    @Test
    public void testShouldUpdateRestaurant() {
        // Arrange
        var restaurant = RestaurantMother.withId(UUID.randomUUID());
        var categories = restaurant.categories();
        categories.add(CategoryMother.withId(UUID.randomUUID()));

        var categoriesIds = categories.stream().map(Category::id).collect(Collectors.toSet());

        var request = new UpdateRestaurantRequest(
                "New name",
                "new description",
                "10:00",
                "22:00",
                categoriesIds
        );
        var command = new UpdateRestaurantCommand(request, restaurant.id());

        when(restaurantRepository.findById(restaurant.id())).thenReturn(Optional.of(restaurant));
        when(categoryRepository.findAllById(request.categories())).thenReturn(categories.stream().toList());
        when(jsonNullableMapper.isPresent(request.description())).thenReturn(true);
        when(jsonNullableMapper.unwrap(request.description())).thenReturn(request.description().get());

        // Act
        var handler = new UpdateRestaurantCommandHandler(restaurantRepository, categoryRepository, jsonNullableMapper);
        handler.handle(command);

        // Assert
        assertEquals(request.name(), restaurant.name());
        assertEquals(request.description().get(), restaurant.description());
        assertEquals(request.openingTime(), restaurant.openingTime());
        assertEquals(request.closingTime(), restaurant.closingTime());
        assertEquals(request.categories(), categoriesIds);

    }

    @Test
    public void testShouldThrowExceptionWhenRestaurantNotFound() {
        // Arrange
        var request = new UpdateRestaurantRequest();
        var command = new UpdateRestaurantCommand(request, UUID.randomUUID());

        when(restaurantRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act && Assert;
        var handler = new UpdateRestaurantCommandHandler(restaurantRepository, categoryRepository, jsonNullableMapper);
        assertThrows(RestaurantNotFoundException.class, () -> {
            handler.handle(command);
        });
    }

    @Test
    public void testShouldThrowExceptionWhenCategoryNotFound() {
        // Arrange
        var restaurant = RestaurantMother.withId(UUID.randomUUID());
        var categories = new HashSet<UUID>();
        categories.add(UUID.randomUUID());
        categories.add(UUID.randomUUID());

        var request = new UpdateRestaurantRequest(
                "New name",
                "new description",
                "10:00",
                "22:00",
                categories
        );
        var command = new UpdateRestaurantCommand(request, restaurant.id());

        when(restaurantRepository.findById(restaurant.id())).thenReturn(Optional.of(restaurant));
        when(categoryRepository.findAllById(request.categories())).thenReturn(Collections.emptyList());

        // Act
        var handler = new UpdateRestaurantCommandHandler(restaurantRepository, categoryRepository, jsonNullableMapper);
        assertThrows(CategoriesDoesntExistException.class, () -> {
            handler.handle(command);
        });
    }


}
