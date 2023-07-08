package commons.cqrs.query;


public class UnsupportedQueryHandlerException extends RuntimeException {
    public UnsupportedQueryHandlerException(Query query) {
        super("No handler found for query: " + query.getClass());
    }

}
