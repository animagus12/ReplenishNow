package org.example.controller;

import org.example.config.DatabaseConnection;
import org.example.model.User;
import org.example.validator.UserValidator;

import java.math.BigDecimal;
import java.sql.*;

public class AdminOperations {

    public static void showAllProducts(User user) throws SQLException {
        if (!UserValidator.isAdmin(user)) {
            System.out.println("Access Denied: Only Admins can perform this operation.");
            return;
        }

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

    public static void addProduct(User user,String productName, double price, int stockCount, String description, String category, String deliveryFrequency) throws SQLException {
        if (!UserValidator.isAdmin(user)) {
            System.out.println("Access Denied: Only Admins can perform this operation.");
            return;
        }

        String query = "INSERT INTO Products (ProductName, Price, StockCount, Description, Category, deliveryFrequency) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, productName);
            pstmt.setBigDecimal(2, BigDecimal.valueOf(price));
            pstmt.setInt(3, stockCount);
            pstmt.setString(4, description);
            pstmt.setString(5, category);
            pstmt.setString(6, deliveryFrequency);
            pstmt.executeUpdate();
            System.out.println("Product added successfully.");
        }
    }

    public static void editProduct(User user,int productId, String productName, double price, int stockCount, String description, String category, String deliveryFrequency) throws SQLException {
        if (!UserValidator.isAdmin(user)) {
            System.out.println("Access Denied: Only Admins can perform this operation.");
            return;
        }

        String query = "UPDATE Products SET ProductName = ?, Price = ?, StockCount = ?, Description = ?, Category = ?, deliveryFrequency = ? WHERE ProductID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, productName);
            pstmt.setBigDecimal(2, BigDecimal.valueOf(price));
            pstmt.setInt(3, stockCount);
            pstmt.setString(4, description);
            pstmt.setString(5, category);
            pstmt.setString(6, deliveryFrequency);
            pstmt.setInt(7, productId);
            pstmt.executeUpdate();
            System.out.println("Product updated successfully.");
        }
    }

    public static void deleteProduct(User user,int productId) throws SQLException {
        if (!UserValidator.isAdmin(user)) {
            System.out.println("Access Denied: Only Admins can perform this operation.");
            return;
        }

        String query = "DELETE FROM Products WHERE ProductID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            pstmt.executeUpdate();
            System.out.println("Product deleted successfully.");
        }
    }

    // Method to search a product by ProductID
    public static void searchProductById(User user,int productId) throws SQLException {
        if (!UserValidator.isAdmin(user)) {
            System.out.println("Access Denied: Only Admins can perform this operation.");
            return;
        }

        String query = "SELECT * FROM Products WHERE ProductID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    displayProductDetails(rs);
                } else {
                    System.out.println("Product with ID " + productId + " not found.");
                }
            }
        }
    }

    // Method to show all order history
    public static void showAllOrderHistory(User user) throws SQLException {
        if (!UserValidator.isAdmin(user)) {
            System.out.println("Access Denied: Only Admins can perform this operation.");
            return;
        }

        String query = "SELECT * FROM Orders";
        try (Connection con = DatabaseConnection.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                displayOrderDetails(rs);
            }
        }
    }

    // Method to show order history by product ID
    public static void showOrderHistoryByProductId(User user,int productId) throws SQLException {
        if (!UserValidator.isAdmin(user)) {
            System.out.println("Access Denied: Only Admins can perform this operation.");
            return;
        }

        String query = "SELECT o.* FROM Orders o " +
                "JOIN Subscriptions s ON o.SubscriptionID = s.SubscriptionID " +
                "JOIN SubscriptionProducts sp ON s.SubscriptionID = sp.SubscriptionID " +
                "WHERE sp.ProductID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, productId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    displayOrderDetails(rs);
                }
            }
        }
    }

    // Method to show order history by user ID
    public static void showOrderHistoryByUserId(User user,String userId) throws SQLException {
        if (!UserValidator.isAdmin(user)) {
            System.out.println("Access Denied: Only Admins can perform this operation.");
            return;
        }

        String query = "SELECT * FROM Orders WHERE UserID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    displayOrderDetails(rs);
                }
            }
        }
    }

    // Method to activate a subscription
    public static void activateSubscription(int subscriptionID) throws SQLException {
        String query = "UPDATE Subscriptions SET SubscriptionStatus = 'ACTIVE' WHERE SubscriptionID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, subscriptionID);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Subscription activated successfully.");
            } else {
                System.out.println("Subscription not found.");
            }
        }
    }

    // Method to deactivate a subscription
    public static void deactivateSubscription(int subscriptionID) throws SQLException {
        String query = "UPDATE Subscriptions SET SubscriptionStatus = 'INACTIVE' WHERE SubscriptionID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, subscriptionID);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Subscription deactivated successfully.");
            } else {
                System.out.println("Subscription not found.");
            }
        }
    }

    // Method to view daily delivery list based on active subscriptions
    public static void viewDailyDeliveryList(Date nextDeliveryDate) throws SQLException {
        String query = "SELECT P.ProductName, PD.NextDeliveryDate, U.FirstName, U.LastName, U.Address " +
                "FROM ProductDeliveryDetails PD " +
                "JOIN SubscriptionProducts SP ON PD.SubscriptionProductID = SP.ID " +
                "JOIN Products P ON SP.ProductID = P.ProductID " +
                "JOIN Orders O ON PD.OrderID = O.OrderID " +
                "JOIN Users U ON O.UserID = U.UserID " +
                "WHERE PD.NextDeliveryDate = ? AND O.OrderStatus = 'ACTIVE'";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setDate(1, nextDeliveryDate);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.isBeforeFirst()) { // No data found
                    System.out.println("No deliveries scheduled for this date.");
                } else {
                    while (rs.next()) {
                        String productName = rs.getString("ProductName");
                        Date deliveryDate = rs.getDate("NextDeliveryDate");
                        String firstName = rs.getString("FirstName");
                        String lastName = rs.getString("LastName");
                        String address = rs.getString("Address");

                        System.out.println("Product: " + productName);
                        System.out.println("Delivery Date: " + deliveryDate);
                        System.out.println("Customer: " + firstName + " " + lastName);
                        System.out.println("Address: " + address);
                        System.out.println("----------------------------------------");
                    }
                }
            }
        }
    }

    // Method to view all active and inactive subscriptions
    public static void viewAllSubscriptions() throws SQLException {
        String query = "SELECT * FROM Subscriptions";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int subscriptionID = rs.getInt("SubscriptionID");
                String subscriptionName = rs.getString("SubscriptionName");
                String subscriptionDescription = rs.getString("SubscriptionDescription");
                String subscriptionCategory = rs.getString("SubscriptionCategory");
                String subscriptionStatus = rs.getString("SubscriptionStatus");
                int subscriptionCount = rs.getInt("SubscriptionCount");

                System.out.println("ID: " + subscriptionID);
                System.out.println("Name: " + subscriptionName);
                System.out.println("Description: " + subscriptionDescription);
                System.out.println("Category: " + subscriptionCategory);
                System.out.println("Status: " + subscriptionStatus);
                System.out.println("Count: " + subscriptionCount);
                System.out.println("----------------------------------------");
            }
        }
    }

    // Method to view order dashboard
    public static void viewOrderDashboard() throws SQLException {
        String query = "SELECT O.OrderID, U.FirstName, U.LastName, P.ProductName, O.OrderDate, O.StartDate, O.EndDate, O.OrderStatus " +
                "FROM Orders O " +
                "JOIN Subscriptions S ON O.SubscriptionID = S.SubscriptionID " +
                "JOIN SubscriptionProducts SP ON S.SubscriptionID = SP.SubscriptionID " +
                "JOIN Products P ON SP.ProductID = P.ProductID " +
                "JOIN Users U ON O.UserID = U.UserID";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int orderID = rs.getInt("OrderID");
                String firstName = rs.getString("FirstName");
                String lastName = rs.getString("LastName");
                String productName = rs.getString("ProductName");
                String orderDate = rs.getString("OrderDate");
                String startDate = rs.getString("StartDate");
                String endDate = rs.getString("EndDate");
                String orderStatus = rs.getString("OrderStatus");

                System.out.println("Order ID: " + orderID);
                System.out.println("Customer: " + firstName + " " + lastName);
                System.out.println("Product: " + productName);
                System.out.println("Order Date: " + orderDate);
                System.out.println("Start Date: " + startDate);
                System.out.println("End Date: " + endDate);
                System.out.println("Status: " + orderStatus);
                System.out.println("----------------------------------------");
            }
        }
    }

    // Helper method to display order details
    private static void displayOrderDetails(ResultSet rs) throws SQLException {
        System.out.println("OrderID: " + rs.getInt("OrderID"));
        System.out.println("UserID: " + rs.getString("UserID"));
        System.out.println("SubscriptionID: " + rs.getInt("SubscriptionID"));
        System.out.println("OrderDate: " + rs.getDate("OrderDate"));
        System.out.println("StartDate: " + rs.getDate("StartDate"));
        System.out.println("EndDate: " + rs.getDate("EndDate"));
        System.out.println("OrderStatus: " + rs.getString("OrderStatus"));
        System.out.println("----");
    }

    // Helper method to display product details from ResultSet
    private static void displayProductDetails(ResultSet rs) throws SQLException {
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

