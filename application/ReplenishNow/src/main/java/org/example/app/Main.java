package org.example.app;

import org.example.controller.HandleLoginOperations;
import org.example.controller.HandleUserOperations;
import org.example.controller.RegistrationOperations;
import org.example.model.Role;
import org.example.model.User;
import org.example.controller.AdminOperations;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        System.out.println("Welcome to the Online Shopping Portal!");
        System.out.println("========================================");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Press 1 to User Registration");
        System.out.println("Press 2 to User Login");
        System.out.println("Press 3 to Forgot Password");
        System.out.println("Press 4 to Exit");
        int n = scanner.nextInt();
        while(true){
            if(n==1){
                System.out.println("User Registration Page");
                User user = RegistrationOperations.createUserFromInput();
                try {
                    if(RegistrationOperations.addUser(user)){
                        HandleUserOperations.handleUserOperations(user);
                    }
                    else {
                        System.out.println("User already exists or You are administering the User as an ADMIN");
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }

            }
            else if(n==2){
                System.out.println("User Login Page");
                HandleLoginOperations.loginUser();
            }
            else if(n==3){
                System.out.println("Forgot Password Page");
                HandleLoginOperations.forgotPassword();
            }
            else if(n==4){
                System.out.println("Exiting...");
                break;
            }
            else{
                System.out.println("Invalid Choice");
            }
        }
       scanner.close();
    }
}