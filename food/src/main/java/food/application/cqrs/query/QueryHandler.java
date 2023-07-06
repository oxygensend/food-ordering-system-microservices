package food.application.cqrs.query;

public interface QueryHandler<T, Q> {
    T handle(Q query);
}
