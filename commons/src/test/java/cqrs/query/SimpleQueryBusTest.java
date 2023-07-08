package cqrs.query;

import commons.cqrs.query.UnsupportedQueryHandlerException;
import commons.cqrs.query.QueryBusRegistry;
import commons.cqrs.query.SimpleQueryBus;
import cqrs.mocks.query.TestQuery;
import cqrs.mocks.query.TestQueryHandler;
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
