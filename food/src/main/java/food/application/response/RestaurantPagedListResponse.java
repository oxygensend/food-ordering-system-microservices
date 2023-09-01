package food.application.response;

import java.util.List;

public record RestaurantPagedListResponse(
        List<RestaurantResponse> data,
        long numberOfElements,
        int numberOfPages

) {
}
