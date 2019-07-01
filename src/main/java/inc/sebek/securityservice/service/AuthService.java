package inc.sebek.securityservice.service;

import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<String> getTokenForUser(String username, String password);
}
