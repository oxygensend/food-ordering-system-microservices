package food.mother;

import food.domain.entity.Category;
import food.domain.entity.Course;
import food.domain.entity.Restaurant;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RestaurantMother {

    public static Restaurant withId(UUID id) {
        Set<Category> categories = new HashSet<>();
        categories.add(CategoryMother.withId(UUID.randomUUID()));
        categories.add(CategoryMother.withId(UUID.randomUUID()));

        Set<Course> courses = new HashSet<>();
        courses.add(CourseMother.withId(UUID.randomUUID()));
        courses.add(CourseMother.withId(UUID.randomUUID()));

        return Restaurant.builder()
                .id(id)
                .name("Test")
                .description("Test description")
                .openingTime("10:00")
                .closingTime("18:00")
                .categories(categories)
                .courses(courses)
                .build();
    }
}
