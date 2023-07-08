package cqrs.query;

import commons.cqrs.query.QueryHandler;
import commons.cqrs.query.QueryBusRegistry;
import cqrs.mocks.query.TestQuery;
import cqrs.mocks.query.TestQueryHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class QueryBusRegistryTest {

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private TestQueryHandler testQueryHandler;


    @Test
    public void testRegistration() {
        // Arrange
        String[] queryHandlers = new String[]{"testQueryHandler"};
        when(applicationContext.getBeanNamesForType(QueryHandler.class)).thenReturn(queryHandlers);

        Class type = TestQueryHandler.class;
        when(applicationContext.getType("testQueryHandler")).thenReturn(type);
        when(applicationContext.getBean(TestQueryHandler.class)).thenReturn(testQueryHandler);

        // Act
        QueryBusRegistry registry = new QueryBusRegistry(applicationContext);
        QueryHandler<Integer,TestQuery> handler = registry.getHandler(TestQuery.class);

        // Assert
        assertInstanceOf(TestQueryHandler.class, handler);
    }
}
