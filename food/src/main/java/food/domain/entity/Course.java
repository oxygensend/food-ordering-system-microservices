package food.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.UUID;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true, fluent = true)
@Entity
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

    @ManyToOne(fetch = FetchType.EAGER)
    private Restaurant restaurant;

}
