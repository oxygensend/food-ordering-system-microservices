package food.infrastructure.repository;

import food.domain.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {

    @Override
    @Query("select r from Restaurant r where r.deletedAt is not null")
    List<Restaurant> findAll();

    @Override
    @Query("select r from Restaurant r where r.deletedAt is null and r.id = :id")
    Optional<Restaurant> findById(@Param("id") UUID id);

    @Query("select r from Restaurant r where r.deletedAt is not null")
    List<Restaurant> recycleBin();

    @Transactional
    @Modifying
    @Query("update Restaurant r set r.deletedAt = CURRENT_DATE where r.id = :id")
    void softDelete(@Param("id") UUID id);
}
