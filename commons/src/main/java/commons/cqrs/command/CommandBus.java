package commons.cqrs.command;

public interface CommandBus {

    <R, C extends Command> R dispatch(C command);
}
