package inc.sebek.securityservice.entity;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum Roles {
    ROLE_ADMIN, ROLE_SPEAKER, ROLE_ORG, ROLE_GUEST;

    public SimpleGrantedAuthority mapToGrantedAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }
}
