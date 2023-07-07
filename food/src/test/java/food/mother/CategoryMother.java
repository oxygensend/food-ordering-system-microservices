package food.mother;

import food.domain.entity.Category;

import java.util.UUID;

public class CategoryMother {

    public static Category withId(UUID id) {
        return Category.builder()
                .id(id)
                .name("Test")
                .description("Test description")
                .build();
    }
}
