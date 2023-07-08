package commons.cqrs.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class CommandBusRegistry {

    private final Map<Class<? extends Command>, Class<? extends CommandHandler<?, ?>>> handlers;
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
        Class<? extends CommandHandler<?, ?>> handlerType = (Class<? extends CommandHandler<?, ?>>) applicationContext.getType(name);
        Class<?>[] genericArguments = GenericTypeResolver.resolveTypeArguments(handlerType, CommandHandler.class);

        assert genericArguments != null;
        Class<? extends Command> commandType = (Class<? extends Command>) genericArguments[1];

        handlers.put(commandType, handlerType);
    }

    @SuppressWarnings("unchecked")
    public <R, C extends Command> CommandHandler<R, C> getHandler(Class<C> commandType) {
        Class<? extends CommandHandler<?, ?>> handlerType = handlers.get(commandType);
        return (CommandHandler<R, C>) applicationContext.getBean(handlerType);
    }
}
