package com.alek0m0m.dronepizzabackend.security;

public class AdminUser {
    private final String username;
    private final String password;
    private final String role;

    public AdminUser() {
        this.username = "admin";
        this.password = "admin1234";
        this.role = "ADMIN";
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}