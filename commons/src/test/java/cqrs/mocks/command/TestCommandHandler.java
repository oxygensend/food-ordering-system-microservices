package cqrs.mocks.command;

import commons.cqrs.command.CommandHandler;

public class TestCommandHandler implements CommandHandler<Object, TestCommand> {

    @Override
    public Object handle(TestCommand command) {

        return new Object();
    }
}
