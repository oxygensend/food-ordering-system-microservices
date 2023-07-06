package food.infrastructure.crqs.query;

import food.application.cqrs.query.UnsupportedQueryHandlerException;
import food.infrastructure.cqrs.query.QueryBusRegistry;
import food.infrastructure.cqrs.query.SimpleQueryBus;
import food.infrastructure.crqs.mocks.query.TestQuery;
import food.infrastructure.crqs.mocks.query.TestQueryHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleQueryBusTest {
    @InjectMocks
    private SimpleQueryBus simpleQueryBus;

    @Mock
    private QueryBusRegistry registry;

    @Mock
    private TestQueryHandler handler;



    @Test
    public void testValidQuery() {

        // Arrange
        TestQuery testQuery = new TestQuery();
        when(registry.getHandler(TestQuery.class)).thenReturn(handler);

        // Act
        simpleQueryBus.dispatch(testQuery);

        // Assert
        verify(handler).handle(testQuery);

    }

    @Test
    public void testInValidQuery() {

        // Arrange
        TestQuery testQuery = new TestQuery();
        when(registry.getHandler(TestQuery.class)).thenReturn(null);

        // Act && Assert
        assertThrows(UnsupportedQueryHandlerException.class, () -> simpleQueryBus.dispatch(testQuery));

    }
}
