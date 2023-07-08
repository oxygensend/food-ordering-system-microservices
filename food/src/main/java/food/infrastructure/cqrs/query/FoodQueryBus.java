package food.infrastructure.cqrs.query;

import commons.cqrs.query.QueryBusRegistry;
import org.springframework.stereotype.Component;

@Component
public class FoodQueryBus extends commons.cqrs.query.SimpleQueryBus {
    public FoodQueryBus(QueryBusRegistry registry) {
        super(registry);
    }
}
