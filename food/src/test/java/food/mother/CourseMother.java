package food.mother;

import food.domain.entity.Course;

import java.util.UUID;

public class CourseMother {


    public static Course withId(UUID id) {
        return Course.builder().name("Test").description("test description").availability(true).cost(10.0F).build();
    }
}
