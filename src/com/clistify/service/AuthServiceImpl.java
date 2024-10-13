package com.clistify.service;

public class AuthServiceImpl implements AuthService {
    private boolean authenticated = false;

    @Override
    public void login(String username, String password) {
        // Here we'll mock the authentication
        if (username.equals("user") && password.equals("password")) {
            authenticated = true;
            System.out.println("Logged in successfully!");
        } else {
            authenticated = false;
            System.out.println("Login failed. Invalid credentials.");
        }
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }
}
