package com.example.authspringsecurityapp.domain.user;

public enum UserRole {
    ADMIN("admin"),
    ANALYST("analyst"),
    USER("user");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}


