package cqrs.mocks.query;

import commons.cqrs.query.QueryHandler;

public class TestQueryHandler implements QueryHandler<Object, TestQuery> {
    @Override
    public Object handle(TestQuery query) {
        return new Object();
    }
}
