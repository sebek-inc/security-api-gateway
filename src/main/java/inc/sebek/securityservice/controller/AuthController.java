package inc.sebek.securityservice.controller;

import inc.sebek.securityservice.dto.AuthRequest;
import inc.sebek.securityservice.dto.AuthResponse;
import inc.sebek.securityservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class AuthController {
    private AuthService authService;

    public AuthController(final AuthService authService) {this.authService = authService;}

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest ar) {
        return authService.getTokenForUser(ar.getUsername(), ar.getPassword())
                          .map(token -> ResponseEntity.ok(new AuthResponse(token)))
                          .defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

}


