package com.example.vehicle_tracker.dto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_CREATE("admin:create"),
    ADMIN_DELETE("admin:delete"),
    STANDARD_READ("standard:read"),
    STANDARD_UPDATE("standard:update"),
    STANDARD_CREATE("standard:create"),
    STANDARD_DELETE("standard:delete")
    ;

    @Getter
    private final String permission;
}
