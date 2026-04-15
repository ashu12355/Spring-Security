package com.ashu.SpringSecurity.entity;

import java.util.Set;

public enum Role {
    ADMIN(Set.of(Permissions.WEATHER_DELETE,
            Permissions.WEATHER_WRITE,
            Permissions.WEATHER_READ
    )),
    USER(Set.of(Permissions.WEATHER_READ));

    private final Set<Permissions> permissions;

    Role(Set<Permissions> permissions) {
        this.permissions = permissions;
    }
    public Set<Permissions> getPermissions() {
        return permissions;
    }
}
