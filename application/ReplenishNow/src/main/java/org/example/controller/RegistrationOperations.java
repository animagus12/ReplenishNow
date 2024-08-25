package org.example.controller;

import org.example.config.DatabaseConnection;
import org.example.model.Role;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;
import java.sql.Date;

public class RegistrationOperations {
    //Method to create a user by taking input
    public static User createUserFromInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter First Name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter Last Name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter Email ID:");
        String emailId = scanner.nextLine();

        System.out.println("Enter Password:");
        String password = scanner.nextLine();

        System.out.println("Enter Address:");
        String address = scanner.nextLine();

        System.out.println("Enter Security Question:");
        String securityQuestion = scanner.nextLine();

        System.out.println("Enter Security Answer:");
        String answer = scanner.nextLine();

        System.out.println("Enter Payment Method (UPI/CARD):");
        String paymentDetails = scanner.nextLine();

        String cardNumber = null;
        String cardCVV = null;
        Date cardExpiryDate = null;
        if (paymentDetails.equalsIgnoreCase("CARD")) {
            System.out.println("Enter Card Number:");
            cardNumber = scanner.nextLine();

            System.out.println("Enter Card CVV:");
            cardCVV = scanner.nextLine();

            System.out.println("Enter Card Expiry Date (YYYY-MM-DD):");
            cardExpiryDate = Date.valueOf(scanner.nextLine());
        }

        System.out.println("Enter Role (ADMIN/CUSTOMER):");
        Role role = Role.valueOf(scanner.nextLine().toUpperCase());

        // Generate a random UUID for the UserID
        String userId = UUID.randomUUID().toString();

        // Create and return the User object
        return new User(userId, firstName, lastName, emailId, password, address,
                securityQuestion, answer, paymentDetails, cardNumber,
                cardCVV, cardExpiryDate, role);
    }

    // Method to add a user to the database
    public static boolean addUser(User user) throws SQLException {
       if(user.getRole().toString().equalsIgnoreCase("ADMIN")){
              System.out.println("Access Denied: Admins cannot be added as users.");
              return false;
         }
        String query = "INSERT INTO Users (UserID, FirstName, LastName, emailId, Password, Address, securityQuestion, " +
                "securityAnswer, paymentMethod, cardNumber, cardCVV, cardExpiryDate, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, user.getUserId());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, user.getAddress());
            pstmt.setString(7, user.getSecurityQuestion());
            pstmt.setString(8, user.getAnswer());
            pstmt.setString(9, user.getPaymentDetails());
            pstmt.setString(10, user.getCardNumber());
            pstmt.setString(11, user.getCardCVV());
            // Check if cardExpiryDate is null
            if (user.getCardExpiryDate() != null) {
                pstmt.setDate(12, new java.sql.Date(user.getCardExpiryDate().getTime()));
            } else {
                pstmt.setNull(12, java.sql.Types.DATE);
            }
            pstmt.setString(13, user.getRole().toString());

            pstmt.executeUpdate();
            System.out.println("User added successfully.");
            return true;
        }
        catch (SQLException e) {
            throw new SQLException("Error adding user to the database.");
        }
       }
    }


