package food.domain.command.restaurant;

import food.domain.command.restaurant.delete.DeleteRestaurantCommand;
import food.domain.command.restaurant.delete.DeleteRestaurantCommandHandler;
import food.domain.exception.RestaurantHasCoursesException;
import food.domain.exception.RestaurantNotFoundException;
import food.infrastructure.repository.RestaurantRepository;
import food.mother.RestaurantMother;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class DeleteRestaurantCommandHandlerTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Test
    public void testShouldDeleteRestaurant() {
        // Arrange
        var restaurant = RestaurantMother.withoutCoursesId(UUID.randomUUID());

        Mockito.when(restaurantRepository.findById(restaurant.id())).thenReturn(Optional.of(restaurant));
        Mockito.doNothing().when(restaurantRepository).softDelete(restaurant.id());

        // Act
        var handler = new DeleteRestaurantCommandHandler(restaurantRepository);
        handler.handle(new DeleteRestaurantCommand(restaurant.id()));

        // Assert
        Mockito.verify(restaurantRepository, Mockito.times(1)).softDelete(restaurant.id());

    }

    @Test
    public void testShouldThrowExceptionWhenRestaurantDoesntExist() {
        // Arrange
        Mockito.when(restaurantRepository.findById(any())).thenReturn(Optional.empty());

        // Act && Assert
        var handler = new DeleteRestaurantCommandHandler(restaurantRepository);
        assertThrows(RestaurantNotFoundException.class, () -> handler.handle(new DeleteRestaurantCommand(UUID.randomUUID())));

    }

    @Test
    public void testShouldThrowExceptionWhenRestaurantHasProducts() {
        // Arrange
        var restaurant = RestaurantMother.withId(UUID.randomUUID());

        Mockito.when(restaurantRepository.findById(any())).thenReturn(Optional.of(restaurant));

        // Act && Assert
        var handler = new DeleteRestaurantCommandHandler(restaurantRepository);
        assertThrows(RestaurantHasCoursesException.class, () -> handler.handle(new DeleteRestaurantCommand(UUID.randomUUID())));

    }
}
