package food.infrastructure.cqrs.query;

import commons.cqrs.query.QueryBusRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FoodQueryBusRegistry extends QueryBusRegistry {
    public FoodQueryBusRegistry(ApplicationContext applicationContext) {
        super(applicationContext);
    }
}
