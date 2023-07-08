package commons.cqrs.query;

public interface QueryHandler<R, Q> {
    R handle(Q query);
}
