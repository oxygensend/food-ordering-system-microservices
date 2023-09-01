package food.application.controller;

import commons.cqrs.command.CommandBus;
import commons.cqrs.query.QueryBus;
import food.application.request.CreateRestaurantRequest;
import food.application.request.UpdateRestaurantRequest;
import food.application.response.GetRestaurantResponse;
import food.application.response.RestaurantIdResponse;
import food.application.response.RestaurantPagedListResponse;
import food.domain.command.restaurant.create.CreateRestaurantCommand;
import food.domain.command.restaurant.delete.DeleteRestaurantCommand;
import food.domain.command.restaurant.update.UpdateRestaurantCommand;
import food.domain.query.restaurant.get.GetRestaurantQuery;
import food.domain.query.restaurant.getAll.GetAllRestaurantQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") UUID id, @RequestBody @Validated UpdateRestaurantRequest request) {
        commandBus.dispatch(new UpdateRestaurantCommand(request, id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        commandBus.dispatch(new DeleteRestaurantCommand(id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RestaurantPagedListResponse getAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) List<UUID> categories,
            Pageable pageable
    ) {
        return queryBus.dispatch(new GetAllRestaurantQuery(pageable, name, search, categories));
    }

}
