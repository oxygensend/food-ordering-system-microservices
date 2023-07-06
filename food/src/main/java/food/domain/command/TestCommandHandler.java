package food.domain.command;

import food.application.cqrs.command.CommandHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestCommandHandler implements CommandHandler<TestCommand> {
    @Override
    public void handle(TestCommand command) {

        log.info("XD");
    }
}
