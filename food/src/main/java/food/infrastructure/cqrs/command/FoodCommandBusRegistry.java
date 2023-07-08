package food.infrastructure.cqrs.command;


import commons.cqrs.command.CommandBusRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class FoodCommandBusRegistry extends CommandBusRegistry {
    public FoodCommandBusRegistry(ApplicationContext applicationContext) {
        super(applicationContext);
    }
}
