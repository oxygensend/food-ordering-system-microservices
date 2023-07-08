package commons.cqrs.command;

public interface CommandBus {

    <C extends Command> void dispatch(C command);
}
