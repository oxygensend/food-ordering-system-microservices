package food.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.NonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@Entity
@Table(name = "food_restaurant")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private String openingTime;

    @Column(nullable = false)
    private String closingTime;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private Set<Course> courses = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "food_restaurant_category",
            joinColumns = @JoinColumn(name = "restaurant_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();


    public Restaurant(UUID id, @NonNull String name, String description, @NonNull String openingTime, @NonNull String closingTime, Set<Course> courses, Set<Category> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.courses = courses;
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
        category.getRestaurants().add(this);
    }

    public void removeCategory(Category category){
        this.categories.remove(category);
        category.getRestaurants().remove(this);
    }

}
