package food.infrastructure.cqrs.command;

import food.application.cqrs.command.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SimpleCommandBus implements CommandBus {

    private final CommandBusRegistry registry;

    public SimpleCommandBus(CommandBusRegistry commandRegistry) {
        this.registry = commandRegistry;

    }

    @Override
    public <C extends Command> void dispatch(C command) {

        @SuppressWarnings("unchecked")
        CommandHandler<C> handler = (CommandHandler<C>) registry.getHandler(command.getClass());
        if (handler == null) {
            throw new UnsupportedCommandHandlerException(command);
        }

        handler.handle(command);
    }
}
