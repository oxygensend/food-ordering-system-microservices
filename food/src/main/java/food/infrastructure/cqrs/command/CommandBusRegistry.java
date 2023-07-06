package food.infrastructure.cqrs.command;

import food.application.cqrs.command.Command;
import food.application.cqrs.command.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class CommandBusRegistry {

    private final Map<Class<? extends Command>, Class<? extends CommandHandler<?>>> handlers;
    private final ApplicationContext applicationContext;

    public CommandBusRegistry(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        this.handlers = new HashMap<>();

        String[] names = applicationContext.getBeanNamesForType(CommandHandler.class);
        for (String name : names) {
            register(name);
        }

    }

    @SuppressWarnings("unchecked")
    private void register(String name) {

        Class<?> type = applicationContext.getType(name);
        Class<? extends CommandHandler<?>> handlerType = (Class<? extends CommandHandler<?>>) applicationContext.getType(name);
        Class<?>[] genericArguments = GenericTypeResolver.resolveTypeArguments(handlerType, CommandHandler.class);

        assert genericArguments != null;
        Class<? extends Command> commandType = (Class<? extends Command>) genericArguments[0];

        handlers.put(commandType, handlerType);
    }

    @SuppressWarnings("unchecked")
    public <C extends Command> CommandHandler<C> getHandler(Class<C> commandType) {
        Class<? extends CommandHandler<?>> handlerType = handlers.get(commandType);
        return (CommandHandler<C>) applicationContext.getBean(handlerType);
    }
}
