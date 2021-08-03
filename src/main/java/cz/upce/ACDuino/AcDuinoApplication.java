package cz.upce.ACDuino;

import cz.upce.ACDuino.entity.UserEntity;
import cz.upce.ACDuino.repository.UserRepository;
import cz.upce.ACDuino.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class AcDuinoApplication {

    private static final Logger logger = LoggerFactory.getLogger(AcDuinoApplication.class);

    @Value("${ACDuino.username}")
    private String username;

    @Value("${ACDuino.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(AcDuinoApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository repository) {
        return args -> {
            if (repository.findByUsername(username) != null) {
                logger.info("Account already exists. Not creating.");
                return;
            }
            String encodedPassword = new BCryptPasswordEncoder().encode(password);
            UserEntity user = new UserEntity(username, encodedPassword);
            logger.info("Creating account: " + repository.save(user));
        };
    }
}
