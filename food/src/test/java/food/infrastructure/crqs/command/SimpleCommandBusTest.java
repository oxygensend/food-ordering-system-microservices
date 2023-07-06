package food.infrastructure.crqs.command;

import food.application.cqrs.command.UnsupportedCommandHandlerException;
import food.infrastructure.cqrs.command.CommandBusRegistry;
import food.infrastructure.cqrs.command.SimpleCommandBus;
import food.infrastructure.crqs.mocks.command.TestCommand;
import food.infrastructure.crqs.mocks.command.TestCommandHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SimpleCommandBusTest {

    @InjectMocks
    private SimpleCommandBus simpleCommandBus;

    @Mock
    private CommandBusRegistry registry;

    @Mock
    private TestCommandHandler handler;



    @Test
    public void testValidCommand() {

        // Arrange
        TestCommand testCommand = new TestCommand();
        when(registry.getHandler(TestCommand.class)).thenReturn(handler);

        // Act
        simpleCommandBus.dispatch(testCommand);

        // Assert
        verify(handler).handle(testCommand);

    }

    @Test
    public void testInValidCommand() {

        // Arrange
        TestCommand testCommand = new TestCommand();
        when(registry.getHandler(TestCommand.class)).thenReturn(null);

        // Act && Assert
        assertThrows(UnsupportedCommandHandlerException.class, () -> simpleCommandBus.dispatch(testCommand));

    }
}
