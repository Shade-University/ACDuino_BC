package cz.upce.ACDuino.service;

import cz.upce.ACDuino.AcDuinoApplication;
import cz.upce.ACDuino.entity.UserEntity;
import cz.upce.ACDuino.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(AcDuinoApplication.class);

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public UserEntity createUser(String username, String password) {
        if (repository.existsByName(username)) {
            log.info("Account already exists. Not creating.");
            return null;
        }

        UserEntity user = new UserEntity(username, encodePassword(password));
        log.info("Creating account: " + repository.save(user));
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUsername(), user.getPassword(), emptyList());
    }


    private String encodePassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }


}
