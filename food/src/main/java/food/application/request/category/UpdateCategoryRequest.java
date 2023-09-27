package food.application.request.category;

import org.openapitools.jackson.nullable.JsonNullable;

public record UpdateCategoryRequest(String name, JsonNullable<String> description) {

    public UpdateCategoryRequest() {
        this(null, JsonNullable.undefined());
    }

    public UpdateCategoryRequest(String name, String description) {
        this(name, JsonNullable.of(description));
    }
}
