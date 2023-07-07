package food.application.controller;

import food.application.cqrs.query.QueryBus;
import food.application.response.GetRestaurantResponse;
import food.domain.query.restaurant.get.GetRestaurantQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/food/restaurants")
@RequiredArgsConstructor
@RestController
public class RestaurantController {

    private final QueryBus queryBus;

    @GetMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    public GetRestaurantResponse show(@PathVariable("id") UUID id) {
        return queryBus.dispatch(new GetRestaurantQuery(id));
    }
}
