package org.example.controller;

import org.example.config.DatabaseConnection;
import org.example.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HandleProductOperations {

    // Method to retrieve product ID based on product name
    public static int getProductIDByName(String productName) throws SQLException {
        String query = "SELECT ProductID FROM Products WHERE ProductName = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, productName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("ProductID");
                } else {
                    System.out.println("Product not found.");
                    return -1;  // Return -1 if product not found
                }
            }
        }
    }

    // Method to retrieve product category based on product name
    public static String getProductCategoryByName(String productName) throws SQLException {
        String query = "SELECT Category FROM Products WHERE ProductName = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, productName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Category");
                } else {
                    System.out.println("Product not found.");
                    return null;
                }
            }
        }
    }

    // Method to retrieve product description based on product name
    public static String getProductDescriptionByName(String productName) throws SQLException {
        String query = "SELECT Description FROM Products WHERE ProductName = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, productName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("Description");
                } else {
                    System.out.println("Product not found.");
                    return null;
                }
            }
        }
    }

    // Method to handle product operations based on user input
    public static void handleProductOperations(User user, String productName) {
        try (Scanner scanner = new Scanner(System.in)) {

            int productID = getProductIDByName(productName);
            String productCategory = getProductCategoryByName(productName);
            String productDescription = getProductDescriptionByName(productName);

            if (productID == -1) {
                System.out.println("Operation aborted due to missing product details.");
                return;
            }

            System.out.println("Choose an operation: 1. Subscribe 2. Unsubscribe 3. Show Subscription 4. Edit Subscription");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
            String userID = user.getUserId();

            switch (choice) {
                case 1:
                    ProductOperations.subscribeProduct(userID, productID,productDescription,productCategory);
                    break;
                case 2:
                    ProductOperations.unsubscribeProduct(userID, productID);
                    break;
                case 3:
                    ProductOperations.showSubscription(userID, productID);
                    break;
                case 4:
                    System.out.println("Enter new subscription name:");
                    String newSubscriptionName = scanner.nextLine();
                    ProductOperations.editSubscription(userID, productID,newSubscriptionName,productCategory, productDescription);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
