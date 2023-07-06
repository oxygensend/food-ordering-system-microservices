package food.infrastructure.crqs.mocks.command;

import food.application.cqrs.command.CommandHandler;

public class TestCommandHandler implements CommandHandler<TestCommand> {

    @Override
    public void handle(TestCommand command) {

    }
}
