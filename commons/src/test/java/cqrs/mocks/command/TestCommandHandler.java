package cqrs.mocks.command;

import commons.cqrs.command.CommandHandler;

public class TestCommandHandler implements CommandHandler<TestCommand> {

    @Override
    public void handle(TestCommand command) {

    }
}
