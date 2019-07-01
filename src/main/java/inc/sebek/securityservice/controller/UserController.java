package inc.sebek.securityservice.controller;

import inc.sebek.securityservice.entity.User;
import inc.sebek.securityservice.service.impls.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
public class UserController {
    private UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {this.userService = userService;}

    @PostMapping("/create")
    Mono<User> createUser(@RequestBody User user) {
        return userService.saveUser(user);
    }
}
