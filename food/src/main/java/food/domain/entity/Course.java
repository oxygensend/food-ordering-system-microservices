package food.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "food_food")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Float cost;

    @Column(nullable = false)
    private boolean availability;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Restaurant restaurant;


}
