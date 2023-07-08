package food.infrastructure.cqrs.command;

import commons.cqrs.command.CommandBusRegistry;
import commons.cqrs.command.SimpleCommandBus;
import org.springframework.stereotype.Component;

@Component
public class FoodCommandBus extends SimpleCommandBus {
    public FoodCommandBus(CommandBusRegistry commandRegistry) {
        super(commandRegistry);
    }
}
