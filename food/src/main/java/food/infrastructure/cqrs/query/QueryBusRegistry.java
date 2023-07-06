package food.infrastructure.cqrs.query;

import food.application.cqrs.command.CommandHandler;
import food.application.cqrs.query.Query;
import food.application.cqrs.query.QueryHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class QueryBusRegistry {

    private final Map<Class<? extends Query>, Class<? extends QueryHandler<?, ?>>> handlers;
    private final ApplicationContext applicationContext;


    public QueryBusRegistry(ApplicationContext applicationContext) {
        this.handlers = new HashMap<>();
        this.applicationContext = applicationContext;

        String[] names = applicationContext.getBeanNamesForType(QueryHandler.class);
        for (String name : names) {
            register(name);
        }
    }

    @SuppressWarnings("unchecked")
    private void register(String name) {
        Class<? extends QueryHandler<?, ?>> handlerType = (Class<? extends QueryHandler<?, ?>>) applicationContext.getType(name);
        Class<?>[] genericArguments = GenericTypeResolver.resolveTypeArguments(handlerType, QueryHandler.class);

        assert genericArguments != null;
        Class<? extends Query> queryType = (Class<? extends Query>) genericArguments[1];

        handlers.put(queryType, handlerType);
    }

    @SuppressWarnings("unchecked")
    public <R, Q extends Query> QueryHandler<R, Q> getHandler(Class<Q> queryType) {
        Class<? extends QueryHandler<?, ?>> handlerType = handlers.get(queryType);
        return (QueryHandler<R, Q>) applicationContext.getBean(handlerType);
    }
}
