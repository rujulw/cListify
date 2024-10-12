package com.clistify.service;

import java.util.Scanner;

public class UserInputService {
    private Scanner scanner;

    public UserInputService() {
        this.scanner = new Scanner(System.in);
    }

    public String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    // Add other input-related methods as needed
}
