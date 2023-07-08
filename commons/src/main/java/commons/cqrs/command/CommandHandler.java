package commons.cqrs.command;

public interface CommandHandler<R, C extends Command> {

    R handle(C command);

}
