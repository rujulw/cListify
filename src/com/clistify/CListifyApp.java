package com.clistify;

import com.clistify.service.AuthService;
import com.clistify.service.AuthServiceImpl;

public class CListifyApp {
    public static void main(String[] args) {
        // Initialize the auth service and start the app
        AuthService authService = new AuthServiceImpl();
        // logic to handle login and playlist management here
    }
}
