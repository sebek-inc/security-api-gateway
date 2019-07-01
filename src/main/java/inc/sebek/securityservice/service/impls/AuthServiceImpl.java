package inc.sebek.securityservice.service.impls;

import inc.sebek.securityservice.security.JWTUtil;
import inc.sebek.securityservice.service.AuthService;
import inc.sebek.securityservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService {
    private PasswordEncoder passwordEncoder;
    private UserService userService;
    private JWTUtil jwtUtil;

    public AuthServiceImpl(final PasswordEncoder passwordEncoder,
                           final UserService userService,
                           final JWTUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public Mono<String> getTokenForUser(String username, String password) {
        return userService.findByUsername(username)
                          .filter(user -> isPasswordCorrect(password, user.getPassword()))
                          .map(user -> jwtUtil.generateToken(user));

    }

    private boolean isPasswordCorrect(String rawPassword, String encodedPassword) {
        return passwordEncoder.encode(rawPassword).equals(encodedPassword);
    }

}
