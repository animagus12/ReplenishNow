package org.example.controller;

import org.example.config.DatabaseConnection;
import org.example.model.User;

import java.sql.*;
import java.util.Scanner;

public class UserOperations {
    // Method to search products by name
    public static void searchProductByName(String productName) throws SQLException {
        String query = "SELECT * FROM Products WHERE ProductName LIKE ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, "%" + productName + "%");
            int i = 0;
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    i=1;
                    System.out.println("ProductID: " + rs.getInt("ProductID"));
                    System.out.println("ProductName: " + rs.getString("ProductName"));
                    System.out.println("Price: " + rs.getBigDecimal("Price"));
                    System.out.println("StockCount: " + rs.getInt("StockCount"));
                    System.out.println("Description: " + rs.getString("Description"));
                    System.out.println("Category: " + rs.getString("Category"));
                    System.out.println("Delivery Frequency: " + rs.getString("deliveryFrequency"));
                    System.out.println("----");
                }
                if(i==1){
                    System.out.println("No products found by this name");
                }
            }
        }
    }

    // Method to show all products
    public static void showAllProducts() throws SQLException {
        String query = "SELECT * FROM Products";

        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                System.out.println("ProductID: " + rs.getInt("ProductID"));
                System.out.println("ProductName: " + rs.getString("ProductName"));
                System.out.println("Price: " + rs.getBigDecimal("Price"));
                System.out.println("StockCount: " + rs.getInt("StockCount"));
                System.out.println("Description: " + rs.getString("Description"));
                System.out.println("Category: " + rs.getString("Category"));
                System.out.println("Delivery Frequency: " + rs.getString("deliveryFrequency"));
                System.out.println("----");
            }
        }
    }

    // Method to view order history
    public static void viewOrderHistory(User user) throws SQLException {
        String query = "SELECT * FROM Orders WHERE UserID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, user.getUserId());
            int  i = 0;
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    i=1;
                    System.out.println("OrderID: " + rs.getInt("OrderID"));
                    System.out.println("SubscriptionID: " + rs.getInt("SubscriptionID"));
                    System.out.println("OrderDate: " + rs.getDate("OrderDate"));
                    System.out.println("StartDate: " + rs.getDate("StartDate"));
                    System.out.println("EndDate: " + rs.getDate("EndDate"));
                    System.out.println("OrderStatus: " + rs.getString("OrderStatus"));
                    System.out.println("----");
                }
                if(i==0){
                    System.out.println("No orders found");
                }
            }
        }
    }

    // Method to view user profile including subscriptions
    public static void viewUserProfile(User user) throws SQLException {
        System.out.println("User Profile:");
        System.out.println("Name: " + user.getFirstName() + " " + user.getLastName());
        System.out.println("Email: " + user.getEmail());
        System.out.println("Address: " + user.getAddress());

        String query = "SELECT * FROM Subscriptions WHERE SubscriptionID IN (SELECT SubscriptionID FROM Orders WHERE UserID = ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, user.getUserId());

            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.println("Subscriptions:");
                while (rs.next()) {
                    System.out.println("Subscription ID: " + rs.getInt("SubscriptionID"));
                    System.out.println("Name: " + rs.getString("SubscriptionName"));
                    System.out.println("Description: " + rs.getString("SubscriptionDescription"));
                    System.out.println("Category: " + rs.getString("SubscriptionCategory"));
                    System.out.println("Status: " + rs.getString("SubscriptionStatus"));
                    System.out.println("Count: " + rs.getInt("SubscriptionCount"));
                    System.out.println("----");
                }
            }
        }
    }

    // Function to show products by category
    public static void showProductsByCategory(String category) throws SQLException {
        String query = "SELECT * FROM Products WHERE Category = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, category);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("ProductID: " + rs.getInt("ProductID"));
                    System.out.println("ProductName: " + rs.getString("ProductName"));
                    System.out.println("Price: " + rs.getBigDecimal("Price"));
                    System.out.println("StockCount: " + rs.getInt("StockCount"));
                    System.out.println("Description: " + rs.getString("Description"));
                    System.out.println("Category: " + rs.getString("Category"));
                    System.out.println("Delivery Frequency: " + rs.getString("deliveryFrequency"));
                    System.out.println("----");
                }
            }
        }
    }

    // Function to view the user's cart
    public static void viewCart(User user) throws SQLException {
        String query = "SELECT c.ID, s.SubscriptionName, s.SubscriptionDescription, s.SubscriptionCategory " +
                "FROM Cart c JOIN Subscriptions s ON c.SubscriptionID = s.SubscriptionID " +
                "WHERE c.UserID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            int i = 0;
            pstmt.setString(1, user.getUserId());
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    i = 1;
                    System.out.println("CartID: " + rs.getInt("ID"));
                    System.out.println("SubscriptionName: " + rs.getString("SubscriptionName"));
                    System.out.println("SubscriptionDescription: " + rs.getString("SubscriptionDescription"));
                    System.out.println("SubscriptionCategory: " + rs.getString("SubscriptionCategory"));
                    System.out.println("----");
                }
                if(i==0){
                    System.out.println("Cart is empty");
                }
            }
        }
    }

    public static void subscriptions(User user) throws SQLException{
        System.out.println("Enter product name you want to subscribe ?");
        Scanner scanner = new Scanner(System.in);
        String productName = scanner.nextLine();
        HandleProductOperations.handleProductOperations(user, productName);
        scanner.close();
    }


    // Method to show all deliverables for a user
    public static void showDeliverables(User user) throws SQLException {
        String query = "SELECT pd.ID AS DeliveryID, p.ProductName, p.Description, pd.NextDeliveryDate, " +
                "sp.Quantity, o.OrderStatus " +
                "FROM ProductDeliveryDetails pd " +
                "JOIN SubscriptionProducts sp ON pd.SubscriptionProductID = sp.ID " +
                "JOIN Products p ON sp.ProductID = p.ProductID " +
                "JOIN Orders o ON pd.OrderID = o.OrderID " +
                "WHERE o.UserID = ? " +
                "AND o.OrderStatus = 'ACTIVE' " +
                "AND pd.NextDeliveryDate >= CURRENT_DATE " +
                "ORDER BY pd.NextDeliveryDate";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, user.getUserId());
            try (ResultSet rs = pstmt.executeQuery()) {
                boolean hasDeliverables = false;
                while (rs.next()) {
                    hasDeliverables = true;
                    System.out.println("Delivery ID: " + rs.getInt("DeliveryID"));
                    System.out.println("Product Name: " + rs.getString("ProductName"));
                    System.out.println("Description: " + rs.getString("Description"));
                    System.out.println("Next Delivery Date: " + rs.getDate("NextDeliveryDate"));
                    System.out.println("Quantity: " + rs.getInt("Quantity"));
                    System.out.println("Order Status: " + rs.getString("OrderStatus"));
                    System.out.println("----");
                }
                if (!hasDeliverables) {
                    System.out.println("No upcoming deliveries found.");
                }
            }
        }
    }


}
