package org.example.controller;

import org.example.model.User;

import java.sql.SQLException;
import java.util.Scanner;

public class HandleLoginOperations {
    public static void loginUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Email ID: ");
        String emailId = scanner.nextLine();

        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        try {
            User user = LoginOperations.getUserByEmailAndPassword(emailId, password);
            if (user != null) {
                System.out.println("Login successful!");
                if(user.getRole().toString().equalsIgnoreCase("ADMIN")){
                    System.out.println("Welcome Admin!");
                    HandleAdminOperations.handleAdminOperations(user);
                }
                else if(user.getRole().toString().equalsIgnoreCase("CUSTOMER")){
                    System.out.println("Welcome Customer!");
                    HandleUserOperations.handleUserOperations(user);
                }
            } else {
                System.out.println("Login failed. Would you like to reset your password? (yes/no)");
                String response = scanner.nextLine();
                if (response.equalsIgnoreCase("yes")) {
                    forgotPassword();
                } else {
                    System.out.println("Exiting...");
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while logging in: " + e.getMessage());
        }
    }

    public static void forgotPassword() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your Email ID: ");
        String emailId = scanner.nextLine();

        try {
            LoginOperations.forgetPassword(emailId);
        } catch (SQLException e) {
            System.out.println("An error occurred while resetting the password: " + e.getMessage());
        }
        scanner.close();
    }

}