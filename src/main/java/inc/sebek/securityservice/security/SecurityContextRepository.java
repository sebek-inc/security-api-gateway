package inc.sebek.securityservice.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static java.util.Objects.nonNull;

@Configuration
public class SecurityContextRepository implements ServerSecurityContextRepository {
    private static final String BEARER_PREFIX = "Bearer ";
    private AuthenticationManager authenticationManager;

    public SecurityContextRepository(final AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new UnsupportedOperationException("Not supported yet.");
        //TODO implement and use in future
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        var authHeader = swe.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (nonNull(authHeader) && authHeader.startsWith(BEARER_PREFIX)) {
            var authToken = authHeader.substring(7);
            var auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
            return this.authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
        } else {
            return Mono.empty();
        }
    }

}

