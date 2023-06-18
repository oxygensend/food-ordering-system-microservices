package food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@SpringBootApplication
@EnableFeignClients(basePackages = "commons.clients")
@PropertySources({
        @PropertySource("classpath:clients-default.properties")
})
public class FoodApplication {
    public static void main(String[] args){
        SpringApplication.run(FoodApplication.class, args);
    }
}
