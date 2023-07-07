package food.application.cqrs.query;

public interface QueryHandler<R, Q> {
    R handle(Q query);
}
