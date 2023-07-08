package food.application.controller;


import food.domain.command.TestCommand;
import commons.cqrs.command.CommandBus;
import commons.cqrs.command.SimpleCommandBus;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/food")
@Slf4j
public class TestController {


    private final CommandBus commandBus;

    public TestController(SimpleCommandBus commandBus) {
        this.commandBus = commandBus;
    }


    @GetMapping("/test")
    public String test(HttpServletRequest request) {

        log.info("xd");
commandBus.dispatch(new TestCommand());
        return "Test";
    }
}
