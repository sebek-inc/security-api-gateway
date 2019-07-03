package inc.sebek.securityservice.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

public enum Roles {
    ROLE_ADMIN, ROLE_SPEAKER, ROLE_ORG, ROLE_GUEST;

    public SimpleGrantedAuthority mapToGrantedAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }

    public static boolean contains(String role) {
        return Arrays.stream(Roles.values()).map(Enum::name).anyMatch(a -> a.equals(role));
    }
}
