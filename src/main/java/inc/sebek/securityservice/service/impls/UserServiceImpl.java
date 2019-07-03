package inc.sebek.securityservice.service.impls;

import inc.sebek.securityservice.entity.Roles;
import inc.sebek.securityservice.entity.User;
import inc.sebek.securityservice.repository.UserRepository;
import inc.sebek.securityservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class UserServiceImpl implements UserService {
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;

    public UserServiceImpl(final PasswordEncoder passwordEncoder, final UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public Mono<User> saveUser(User user) {
        //Temporary workaround for creating users. Will be removed after adding registration
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(Roles.ROLE_ADMIN));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    public Mono<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
