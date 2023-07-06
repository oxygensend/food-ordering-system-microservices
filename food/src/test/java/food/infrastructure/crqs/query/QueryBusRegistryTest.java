package food.infrastructure.crqs.query;

import food.application.cqrs.query.QueryHandler;
import food.infrastructure.cqrs.query.QueryBusRegistry;
import food.infrastructure.crqs.mocks.query.TestQuery;
import food.infrastructure.crqs.mocks.query.TestQueryHandler;
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
