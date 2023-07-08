package commons.cqrs.command;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleCommandBus implements CommandBus {

    private final CommandBusRegistry registry;

    public SimpleCommandBus(CommandBusRegistry commandRegistry) {
        this.registry = commandRegistry;

    }

    @Override
    public <R, C extends Command> R dispatch(C command) {

        @SuppressWarnings("unchecked")
        CommandHandler<R, C> handler = (CommandHandler<R, C>) registry.getHandler(command.getClass());
        if (handler == null) {
            throw new UnsupportedCommandHandlerException(command);
        }

        return handler.handle(command);
    }
}
