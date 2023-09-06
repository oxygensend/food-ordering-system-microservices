package food.domain.query.category;

import food.application.response.restaurant.RestaurantResponse;
import food.domain.entity.Restaurant;
import food.domain.exception.CategoryNotFoundException;
import food.domain.query.category.get.GetCategoryQuery;
import food.domain.query.category.get.GetCategoryQueryHandler;
import food.infrastructure.repository.CategoryRepository;
import food.mother.CategoryMother;
import food.mother.RestaurantMother;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCategoryCommandHandlerTest {

    @InjectMocks
    private GetCategoryQueryHandler handler;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    public void testShouldFindCategory() {
        //Arrange
        var category = CategoryMother.withId(UUID.randomUUID());
        var restaurants = new HashSet<Restaurant>();
        restaurants.add(RestaurantMother.withId(UUID.randomUUID()));
        restaurants.add(RestaurantMother.withId(UUID.randomUUID()));
        category.restaurants(restaurants);

        var restaurantsResponse = restaurants.stream().map(restaurant -> {
            return new RestaurantResponse(
                    restaurant.id(),
                    restaurant.name(),
                    restaurant.description(),
                    restaurant.openingTime(),
                    restaurant.closingTime()
            );
        }).collect(Collectors.toSet());

        var query = new GetCategoryQuery(category.id());

        when(categoryRepository.findById(any(UUID.class))).thenReturn(Optional.of(category));

        //Act
        var response = handler.handle(query);

        //Assert
        assertEquals(response.id(), category.id());
        assertEquals(response.name(), category.name());
        assertEquals(response.description(), category.description());
        assertEquals(response.restaurants(), restaurantsResponse);
    }

    @Test
    public void testShouldThrowNotFoundException() {
        // Arrange
        var query = new GetCategoryQuery(UUID.randomUUID());

        when(categoryRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act && Assert;
        assertThrows(CategoryNotFoundException.class, () -> {
            handler.handle(query);
        });
    }
}
