package food.application.cqrs.command;

public interface CommandBus {

    <C extends Command> void dispatch(C command);
}
