package commons.cqrs.query;

public interface QueryHandler<R, Q extends Query> {
    R handle(Q query);
}
