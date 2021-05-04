package cz.upce.ACDuino;

import cz.upce.ACDuino.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AcDuinoApplication {

    @Value("${ACDuino.username}")
    private String username;

    @Value("${ACDuino.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(AcDuinoApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserService userService) {
        return args -> {
            userService.createUser(username, password);
        };
    }

}
