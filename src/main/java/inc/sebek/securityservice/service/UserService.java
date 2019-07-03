package inc.sebek.securityservice.service;

import inc.sebek.securityservice.entity.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> findByUsername(String username);
}
