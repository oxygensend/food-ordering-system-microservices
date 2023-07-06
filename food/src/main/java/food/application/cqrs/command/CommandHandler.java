package food.application.cqrs.command;

public interface CommandHandler<C extends Command> {

    void handle(C command);

}
