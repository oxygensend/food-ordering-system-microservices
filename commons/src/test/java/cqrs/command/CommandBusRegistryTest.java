package cqrs.command;

import commons.cqrs.command.CommandHandler;
import commons.cqrs.command.CommandBusRegistry;
import cqrs.mocks.command.TestCommand;
import cqrs.mocks.command.TestCommandHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandBusRegistryTest {
    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private TestCommandHandler testCommandHandler;


    @Test
    public void testRegistration() {
        // Arrange
        String[] commandHandlers = new String[]{"testCommandHandler"};
        when(applicationContext.getBeanNamesForType(CommandHandler.class)).thenReturn(commandHandlers);

        Class type = TestCommandHandler.class;
        when(applicationContext.getType("testCommandHandler")).thenReturn(type);
        when(applicationContext.getBean(TestCommandHandler.class)).thenReturn(testCommandHandler);

        // Act
        CommandBusRegistry registry = new CommandBusRegistry(applicationContext);
        CommandHandler<Integer,TestCommand> handler = registry.getHandler(TestCommand.class);

        // Assert
        assertInstanceOf(TestCommandHandler.class, handler);
    }


}
