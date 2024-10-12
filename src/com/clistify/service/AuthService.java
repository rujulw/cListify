package com.clistify.service;

public interface AuthService {
    void login(String username, String password);
    boolean isAuthenticated();
    // relevant methods needed
}
