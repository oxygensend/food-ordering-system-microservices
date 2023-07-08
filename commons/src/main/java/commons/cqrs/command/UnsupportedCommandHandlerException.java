package commons.cqrs.command;

public class UnsupportedCommandHandlerException extends RuntimeException {
    public UnsupportedCommandHandlerException(Command command) {
        super("No handler found for command: " + command.getClass());
    }
}
