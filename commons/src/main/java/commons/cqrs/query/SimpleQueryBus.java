package commons.cqrs.query;


public class SimpleQueryBus implements QueryBus {

    private final QueryBusRegistry registry;

    public SimpleQueryBus(QueryBusRegistry registry) {
        this.registry = registry;
    }


    @Override
    public <R, Q extends Query> R dispatch(Q query) {
        @SuppressWarnings("unchecked")
        QueryHandler<R, Q> handler = (QueryHandler<R, Q>) registry.getHandler(query.getClass());
        if (handler == null) {
            throw new UnsupportedQueryHandlerException(query);
        }

        return handler.handle(query);
    }
}
