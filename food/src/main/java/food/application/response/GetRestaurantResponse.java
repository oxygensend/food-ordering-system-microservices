package food.application.response;

import java.util.Set;
import java.util.UUID;

public record GetRestaurantResponse(UUID id,
                                    String name,
                                    String description,
                                    String openingTime,
                                    String closingTime,
                                    Set<String> categories
) {
}
