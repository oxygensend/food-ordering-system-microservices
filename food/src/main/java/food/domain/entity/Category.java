package food.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
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

    @ManyToMany(mappedBy = "categories")
    private Set<Restaurant> restaurants;

    public Category() {
        this.restaurants = new HashSet<>();
    }

    public Category(UUID id, String name, String description, Set<Restaurant> restaurants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.restaurants = restaurants;
    }
}
