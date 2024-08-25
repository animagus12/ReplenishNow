package org.example.controller;

import org.example.config.DatabaseConnection;
import org.example.model.Role;
import org.example.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Scanner;

public class LoginOperations {
    // Method to fetch a user from the database by emailId and password
    public static User getUserByEmailAndPassword(String emailId, String password) throws SQLException {
        String query = "SELECT * FROM Users WHERE emailId = ? AND Password = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, emailId);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String userId = rs.getString("UserID");
                    String firstName = rs.getString("FirstName");
                    String lastName = rs.getString("LastName");
                    String address = rs.getString("Address");
                    String securityQuestion = rs.getString("securityQuestion");
                    String securityAnswer = rs.getString("securityAnswer");
                    String paymentMethod = rs.getString("paymentMethod");
                    String cardNumber = rs.getString("cardNumber");
                    String cardCVV = rs.getString("cardCVV");
                    Date cardExpiryDate = rs.getDate("cardExpiryDate");
                    Role role = Role.valueOf(rs.getString("role"));

                    return new User(userId, firstName, lastName, emailId, password, address,
                            securityQuestion, securityAnswer, paymentMethod,
                            cardNumber, cardCVV, cardExpiryDate, role);
                } else {
                    System.out.println("User with email " + emailId + " and provided password not found.");
                    return null;
                }
            }
        }
    }

    // Method to reset the password using security question and answer
    public static void forgetPassword(String emailId) throws SQLException {
        String query = "SELECT securityQuestion, securityAnswer FROM Users WHERE emailId = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, emailId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String securityQuestion = rs.getString("securityQuestion");
                    String correctAnswer = rs.getString("securityAnswer");

                    Scanner scanner = new Scanner(System.in);
                    System.out.println("Security Question: " + securityQuestion);
                    System.out.print("Answer: ");
                    String providedAnswer = scanner.nextLine();

                    if (correctAnswer.equals(providedAnswer)) {
                        System.out.print("Enter new password: ");
                        String newPassword = scanner.nextLine();

                        // Update password in the database
                        String updateQuery = "UPDATE Users SET Password = ? WHERE emailId = ?";
                        try (PreparedStatement updatePstmt = con.prepareStatement(updateQuery)) {
                            updatePstmt.setString(1, newPassword);
                            updatePstmt.setString(2, emailId);
                            updatePstmt.executeUpdate();
                            System.out.println("Password updated successfully.");
                        }
                        scanner.close();
                    } else {
                        System.out.println("Incorrect answer to the security question.");
                    }
                } else {
                    System.out.println("User with email " + emailId + " not found.");
                }
            }
        }
    }
}
