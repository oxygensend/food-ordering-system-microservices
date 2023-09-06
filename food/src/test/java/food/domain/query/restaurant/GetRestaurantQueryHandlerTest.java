package food.domain.query.restaurant;

import food.application.response.restaurant.GetRestaurantResponse;
import food.domain.entity.Category;
import food.domain.entity.Restaurant;
import food.domain.exception.RestaurantNotFoundException;
import food.domain.query.restaurant.get.GetRestaurantQuery;
import food.domain.query.restaurant.get.GetRestaurantQueryHandler;
import food.infrastructure.repository.RestaurantRepository;
import food.mother.RestaurantMother;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GetRestaurantQueryHandlerTest {

    private GetRestaurantQueryHandler handler;

    @Mock
    private RestaurantRepository repository;


    @Test
    public void testShouldFindRestaurant() {
        // Arrange
        Restaurant restaurant = RestaurantMother.withId(UUID.randomUUID());
        GetRestaurantQuery query = new GetRestaurantQuery(restaurant.id());

        Set<String> categories = restaurant.categories().stream().map(Category::name).collect(Collectors.toSet());
        GetRestaurantResponse expectedResponse = new GetRestaurantResponse(
                restaurant.id(),
                restaurant.name(),
                restaurant.description(),
                restaurant.openingTime(),
                restaurant.closingTime(),
                categories
        );

        when(repository.findById(restaurant.id())).thenReturn(Optional.of(restaurant));

        // Act
        handler = new GetRestaurantQueryHandler(repository);
        GetRestaurantResponse response = handler.handle(query);

        // Assert
        assertEquals(response, expectedResponse);
    }

    @Test
    public void testShouldThrowNotFoundException() {
        // Arrange
        GetRestaurantQuery query = new GetRestaurantQuery(UUID.randomUUID());

        when(repository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act && Assert;
        handler = new GetRestaurantQueryHandler(repository);
        assertThrows(RestaurantNotFoundException.class, () -> {
            handler.handle(query);
        });
    }


}
