package cqrs.command;

import commons.cqrs.command.UnsupportedCommandHandlerException;
import commons.cqrs.command.CommandBusRegistry;
import commons.cqrs.command.SimpleCommandBus;
import cqrs.mocks.command.TestCommand;
import cqrs.mocks.command.TestCommandHandler;
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
