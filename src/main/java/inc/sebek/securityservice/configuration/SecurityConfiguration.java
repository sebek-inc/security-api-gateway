package inc.sebek.securityservice.configuration;

import inc.sebek.securityservice.security.AuthenticationManager;
import inc.sebek.securityservice.security.SecurityContextRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfiguration {
    private AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository;

    public SecurityConfiguration(final AuthenticationManager authenticationManager,
                                 final SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http.exceptionHandling()
                   .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)))
                   .accessDeniedHandler((swe, e) -> Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)))
                   .and()
                   .csrf().disable()
                   .formLogin().disable()
                   .httpBasic().disable()
                   .authenticationManager(authenticationManager)
                   .securityContextRepository(securityContextRepository)
                   .authorizeExchange()
                   .pathMatchers(HttpMethod.OPTIONS).permitAll()
                   .pathMatchers("/resource/user").authenticated()
                   .pathMatchers("/login").permitAll()
                   .pathMatchers("/create").permitAll()
                   .anyExchange().authenticated()
                   .and()
                   .build();
    }
}

