package food.infrastructure;

import food.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository  extends JpaRepository<Category, UUID> {
}
