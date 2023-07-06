package food.application.cqrs.query;

public interface QueryBus {

    <R, Q extends Query> R dispatch(Q command);
}
