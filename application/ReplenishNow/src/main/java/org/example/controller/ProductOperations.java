package org.example.controller;
import org.example.config.DatabaseConnection;
import java.sql.*;

public class ProductOperations {

    // Method to subscribe to a product
    public static void subscribeProduct(String userId, int productId, String description, String category) throws SQLException {
        // Check if the user has already subscribed to the product
        if (isProductSubscribed(userId, productId)) {
            System.out.println("Product already subscribed.");
            return;
        }

        // Insert the subscription into the Subscriptions table
        String subscribeQuery = "INSERT INTO Subscriptions (SubscriptionName, SubscriptionDescription, SubscriptionCategory, SubscriptionStatus) " +
                "VALUES (?, ?, ?, 'ACTIVE')";
        String linkSubscriptionQuery = "INSERT INTO SubscriptionProducts (SubscriptionID, ProductID, Quantity) VALUES (?, ?, 1)";
        String addToCartQuery = "INSERT INTO Cart (UserID, SubscriptionID) VALUES (?, ?)";

        try (Connection con = DatabaseConnection.getConnection()) {
            // Start a transaction
            con.setAutoCommit(false);

            try (PreparedStatement subscribeStmt = con.prepareStatement(subscribeQuery, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement linkSubscriptionStmt = con.prepareStatement(linkSubscriptionQuery);
                 PreparedStatement addToCartStmt = con.prepareStatement(addToCartQuery)) {

                // Step 1: Add a new subscription
                subscribeStmt.setString(1, "Subscription for Product " + productId);
                subscribeStmt.setString(2, description);
                subscribeStmt.setString(3, category);
                subscribeStmt.executeUpdate();

                // Get the generated subscription ID
                ResultSet rs = subscribeStmt.getGeneratedKeys();
                int subscriptionId = 0;
                if (rs.next()) {
                    subscriptionId = rs.getInt(1);
                }

                // Step 2: Link the subscription with the product
                linkSubscriptionStmt.setInt(1, subscriptionId);
                linkSubscriptionStmt.setInt(2, productId);
                linkSubscriptionStmt.executeUpdate();

                // Step 3: Add the subscription to the user's cart
                addToCartStmt.setString(1, userId);
                addToCartStmt.setInt(2, subscriptionId);
                addToCartStmt.executeUpdate();

                // Commit the transaction
                con.commit();
                System.out.println("Product subscribed successfully and added to cart.");
            } catch (SQLException e) {
                // Rollback the transaction in case of an error
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    // Method to unsubscribe from a product
    public static void unsubscribeProduct(String userId, int productId) throws SQLException {
        // Check if the user has subscribed to the product
        if (!isProductSubscribed(userId, productId)) {
            System.out.println("Product not subscribed.");
            return;
        }

        // Delete the subscription and remove it from the cart
        String deleteSubscriptionQuery = "DELETE sp, s FROM SubscriptionProducts sp " +
                "JOIN Subscriptions s ON sp.SubscriptionID = s.SubscriptionID " +
                "WHERE sp.ProductID = ? AND s.SubscriptionID IN " +
                "(SELECT SubscriptionID FROM Cart WHERE UserID = ?)";

        String removeFromCartQuery = "DELETE FROM Cart WHERE UserID = ? AND SubscriptionID IN " +
                "(SELECT SubscriptionID FROM Subscriptions WHERE SubscriptionID IN " +
                "(SELECT SubscriptionID FROM SubscriptionProducts WHERE ProductID = ?))";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement deleteSubscriptionStmt = con.prepareStatement(deleteSubscriptionQuery);
             PreparedStatement removeFromCartStmt = con.prepareStatement(removeFromCartQuery)) {

            // Start a transaction
            con.setAutoCommit(false);

            try {
                // Step 1: Delete the subscription and linked products
                deleteSubscriptionStmt.setInt(1, productId);
                deleteSubscriptionStmt.setString(2, userId);
                deleteSubscriptionStmt.executeUpdate();

                // Step 2: Remove the subscription from the user's cart
                removeFromCartStmt.setString(1, userId);
                removeFromCartStmt.setInt(2, productId);
                removeFromCartStmt.executeUpdate();

                // Commit the transaction
                con.commit();
                System.out.println("Product unsubscribed and removed from cart successfully.");
            } catch (SQLException e) {
                // Rollback the transaction in case of an error
                con.rollback();
                throw e;
            } finally {
                con.setAutoCommit(true);
            }
        }
    }

    // Method to show the user's subscription for a particular product
    public static void showSubscription(String userId, int productId) throws SQLException {
        if (!isProductSubscribed(userId, productId)) {
            System.out.println("No subscription yet.");
            return;
        }

        String query = "SELECT s.SubscriptionID, s.SubscriptionName, s.SubscriptionDescription, s.SubscriptionCategory, s.SubscriptionStatus " +
                "FROM Subscriptions s " +
                "JOIN SubscriptionProducts sp ON s.SubscriptionID = sp.SubscriptionID " +
                "JOIN Cart c ON c.SubscriptionID = s.SubscriptionID " +
                "WHERE c.UserID = ? AND sp.ProductID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, userId);
            pstmt.setInt(2, productId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    System.out.println("SubscriptionID: " + rs.getInt("SubscriptionID"));
                    System.out.println("SubscriptionName: " + rs.getString("SubscriptionName"));
                    System.out.println("SubscriptionDescription: " + rs.getString("SubscriptionDescription"));
                    System.out.println("SubscriptionCategory: " + rs.getString("SubscriptionCategory"));
                    System.out.println("SubscriptionStatus: " + rs.getString("SubscriptionStatus"));
                    System.out.println("----");
                }
            }
        }
    }

    // Method to edit the user's subscription for a particular product
    public static void editSubscription(String userId, int productId, String newSubscriptionName, String newSubscriptionDescription, String newCategory) throws SQLException {
        if (!isProductSubscribed(userId, productId)) {
            System.out.println("No subscription to edit.");
            return;
        }

        String query = "UPDATE Subscriptions SET SubscriptionName = ?, SubscriptionDescription = ?, SubscriptionCategory = ? " +
                "WHERE SubscriptionID IN (SELECT sp.SubscriptionID FROM SubscriptionProducts sp " +
                "JOIN Cart c ON c.SubscriptionID = sp.SubscriptionID WHERE c.UserID = ? AND sp.ProductID = ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, newSubscriptionName);
            pstmt.setString(2, newSubscriptionDescription);
            pstmt.setString(3, newCategory);
            pstmt.setString(4, userId);
            pstmt.setInt(5, productId);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Subscription updated successfully.");
            } else {
                System.out.println("No subscription found to update.");
            }
        }
    }

    // Helper method to check if a product is subscribed by the user
    private static boolean isProductSubscribed(String userId, int productId) throws SQLException {
        String query = "SELECT 1 FROM SubscriptionProducts sp " +
                "JOIN Cart c ON c.SubscriptionID = sp.SubscriptionID " +
                "WHERE c.UserID = ? AND sp.ProductID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, userId);
            pstmt.setInt(2, productId);

            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        }
    }
}
