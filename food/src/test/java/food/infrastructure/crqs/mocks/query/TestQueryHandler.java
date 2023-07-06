package food.infrastructure.crqs.mocks.query;

import food.application.cqrs.query.QueryHandler;

public class TestQueryHandler implements QueryHandler<Object, TestQuery> {
    @Override
    public Object handle(TestQuery query) {
        return new Object();
    }
}
