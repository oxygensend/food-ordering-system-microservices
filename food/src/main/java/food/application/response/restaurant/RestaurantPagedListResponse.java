package food.application.response.restaurant;

import java.util.List;

public record RestaurantPagedListResponse(
        List<RestaurantResponse> data,
        long numberOfElements,
        int numberOfPages

) {
}
