package food.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
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
@Table(name = "food_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "categories", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Restaurant> restaurants = new HashSet<>();

    @Column(nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    @Column
    @UpdateTimestamp
    private LocalDateTime updatedAt;



    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

}
