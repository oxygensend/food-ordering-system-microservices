package food.domain.specification;

import food.domain.entity.Category;
import food.domain.entity.Restaurant;
import jakarta.persistence.criteria.*;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

@Data
@Builder
public class RestaurantSpecification implements Specification<Restaurant> {

    private String name;
    private String searchTerm;
    private List<UUID> categories;

    @Override
    public Predicate toPredicate(Root<Restaurant> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        Predicate namePredicate = Optional.ofNullable(name).map(value -> equals(cb, root.get("name"), value)).orElse(null);
        Predicate searchPredicate = search(cb, root, searchTerm);
        Predicate categoriesPredicate = categories(cb, root, categories);

        Optional.ofNullable(namePredicate).ifPresent(predicates::add);
        Optional.ofNullable(searchPredicate).ifPresent(predicates::add);
        Optional.ofNullable(categoriesPredicate).ifPresent(predicates::add);

        return cb.and(predicates.toArray(new Predicate[0]));
    }

    private Predicate categories(CriteriaBuilder cb, Root<Restaurant> root, List<UUID> categories) {
        if (categories == null || categories.isEmpty()) {
            return null;
        }

        Join<Restaurant, Category> categoryJoin = root.join("categories", JoinType.LEFT);
        return categoryJoin.get("id").in(categories);
    }

    private Predicate search(CriteriaBuilder cb, Root<Restaurant> root, String searchTerm) {
        if (StringUtils.isBlank(searchTerm)) {
            return null;
        }

        Join<Restaurant, Category> categoryJoin = root.join("categories", JoinType.LEFT);
        return cb.or(
                like(cb, root.get("name"), searchTerm),
                like(cb, root.get("description"), searchTerm),
                like(cb, categoryJoin.get("name"), searchTerm),
                like(cb, categoryJoin.get("description"), searchTerm)
        );
    }

    private Predicate equals(CriteriaBuilder cb, Path<Object> field, Object value) {
        return cb.equal(field, value);
    }

    private Predicate like(CriteriaBuilder cb, Path<String> field, String value) {
        return cb.like(cb.lower(field), "%" + value.toLowerCase() + "%");
    }

}
