package food.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true, fluent = true)
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

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column()
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column()
    private LocalDateTime deletedAt;


    public void addCategory(Category category) {
        this.categories.add(category);
        category.restaurants().add(this);
    }

    public void removeCategory(Category category) {
        this.categories.remove(category);
        category.restaurants().remove(this);
    }


    public void addCourse(Course course) {
        this.courses.add(course);
        course.restaurant(this);
    }

    public void removeCourse(Course course) {
        this.courses.remove(course);
        course.restaurant(null);
    }
}
