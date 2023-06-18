package food.application.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/food")
public class TestController {


    @GetMapping("/test")
    public String test(HttpServletRequest request){

        return "Test";
    }
}
