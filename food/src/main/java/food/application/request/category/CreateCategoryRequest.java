package food.application.request.category;

import jakarta.validation.constraints.NotBlank;

public record CreateCategoryRequest(
        @NotBlank
        String name,
        @NotBlank
        String description
) {
}
