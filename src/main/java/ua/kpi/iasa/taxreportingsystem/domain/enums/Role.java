package ua.kpi.iasa.taxreportingsystem.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_GUEST,
    ROLE_USER,
    ROLE_INSPECTOR,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
