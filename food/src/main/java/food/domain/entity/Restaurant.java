package food.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "food_restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @NonNull
    @Column(nullable = false)
    private String name;

    private String description;

    @NonNull
    @Column(nullable = false)
    private String openingTime;

    @NonNull
    @Column(nullable = false)
    private String closingTime;

    @OneToMany(mappedBy = "restaurant")
    private Set<Course> courses;

    @ManyToMany
    @JoinTable(
            name = "food_restaurant_category",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")

    )
    private Set<Category> categories;

    public Restaurant() {
        this.courses = new HashSet<>();
        this.categories = new HashSet<>();
    }

    public Restaurant(UUID id, @NonNull String name, String description, @NonNull String openingTime, @NonNull String closingTime, Set<Course> courses, Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.courses = courses;
        this.categories = categories;
    }
}
