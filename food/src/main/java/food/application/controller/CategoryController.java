package food.application.controller;

import commons.cqrs.command.CommandBus;
import commons.cqrs.query.QueryBus;
import food.application.request.category.CreateCategoryRequest;
import food.application.request.category.UpdateCategoryRequest;
import food.application.response.category.CategoryIdResponse;
import food.application.response.category.CategoryPagedListResponse;
import food.application.response.restaurant.GetRestaurantResponse;
import food.domain.command.category.create.CreateCategoryCommand;
import food.domain.command.category.delete.DeleteCategoryCommand;
import food.domain.command.category.update.UpdateCategoryCommand;
import food.domain.query.category.get.GetCategoryQuery;
import food.domain.query.category.getAll.GetAllCategoriesQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/food/categories")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CommandBus commandBus;
    private final QueryBus queryBus;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryIdResponse create(@RequestBody @Validated CreateCategoryRequest request) {
        return commandBus.dispatch(new CreateCategoryCommand(request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        commandBus.dispatch(new DeleteCategoryCommand(id));
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GetRestaurantResponse show(@PathVariable("id") UUID id) {
        return queryBus.dispatch(new GetCategoryQuery(id));
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryIdResponse update(@PathVariable("id") UUID id, @RequestBody @Validated UpdateCategoryRequest request) {
        return commandBus.dispatch(new UpdateCategoryCommand(request, id));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<CategoryPagedListResponse> getAll(Pageable pageable) {
        return queryBus.dispatch(new GetAllCategoriesQuery(pageable));
    }
}
