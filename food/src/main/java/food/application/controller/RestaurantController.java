package food.application.controller;

import commons.cqrs.command.CommandBus;
import commons.cqrs.query.QueryBus;
import food.application.request.CreateRestaurantRequest;
import food.application.response.GetRestaurantResponse;
import food.application.response.RestaurantIdResponse;
import food.domain.command.restaurant.create.CreateRestaurantCommand;
import food.domain.query.restaurant.get.GetRestaurantQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/food/restaurants")
@RequiredArgsConstructor
@RestController
public class RestaurantController {

    private final QueryBus queryBus;
    private final CommandBus commandBus;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetRestaurantResponse show(@PathVariable("id") UUID id) {
        return queryBus.dispatch(new GetRestaurantQuery(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestaurantIdResponse create(@RequestBody @Validated CreateRestaurantRequest request) {
        return commandBus.dispatch(new CreateRestaurantCommand(request));
    }
}
