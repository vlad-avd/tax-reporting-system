package ua.kpi.iasa.taxreportingsystem.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    INSPECTOR,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
