package org.example.controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.example.controller.UserOperations;
import org.example.config.DatabaseConnection;
import org.example.model.Role;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

class UserOperationsTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
        mockResultSet = Mockito.mock(ResultSet.class);
    }

    @Test
    void searchProductByNameDisplaysProductsWhenFound() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true, false);
            Mockito.when(mockResultSet.getInt("ProductID")).thenReturn(1);
            Mockito.when(mockResultSet.getString("ProductName")).thenReturn("Product1");
            Mockito.when(mockResultSet.getBigDecimal("Price")).thenReturn(new BigDecimal("10.00"));
            Mockito.when(mockResultSet.getInt("StockCount")).thenReturn(100);
            Mockito.when(mockResultSet.getString("Description")).thenReturn("Description1");
            Mockito.when(mockResultSet.getString("Category")).thenReturn("Category1");
            Mockito.when(mockResultSet.getString("deliveryFrequency")).thenReturn("Weekly");

            UserOperations.searchProductByName("Product1");

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery();
        }
    }

    @Test
    void searchProductByNameDisplaysNoProductsWhenNotFound() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(false);

            UserOperations.searchProductByName("NonExistentProduct");

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery();
        }
    }

    @Test
    void showAllProductsDisplaysAllProducts() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.createStatement()).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery(Mockito.anyString())).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true, false);
            Mockito.when(mockResultSet.getInt("ProductID")).thenReturn(1);
            Mockito.when(mockResultSet.getString("ProductName")).thenReturn("Product1");
            Mockito.when(mockResultSet.getBigDecimal("Price")).thenReturn(new BigDecimal("10.00"));
            Mockito.when(mockResultSet.getInt("StockCount")).thenReturn(100);
            Mockito.when(mockResultSet.getString("Description")).thenReturn("Description1");
            Mockito.when(mockResultSet.getString("Category")).thenReturn("Category1");
            Mockito.when(mockResultSet.getString("deliveryFrequency")).thenReturn("Weekly");

            UserOperations.showAllProducts();

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery(Mockito.anyString());
        }
    }

    @Test
    void viewOrderHistoryDisplaysOrdersWhenFound() throws SQLException {
        User user = new User("1", "John", "Doe", "john.doe@example.com", "password", "123 Main St",
                "What is your pet's name?", "Fluffy", "Credit Card", "1234567890123456",
                "123", Date.valueOf("2025-12-31"), Role.CUSTOMER);

        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true, false);
            Mockito.when(mockResultSet.getInt("OrderID")).thenReturn(1);
            Mockito.when(mockResultSet.getInt("SubscriptionID")).thenReturn(1);
            Mockito.when(mockResultSet.getDate("OrderDate")).thenReturn(Date.valueOf("2023-01-01"));
            Mockito.when(mockResultSet.getDate("StartDate")).thenReturn(Date.valueOf("2023-01-01"));
            Mockito.when(mockResultSet.getDate("EndDate")).thenReturn(Date.valueOf("2023-12-31"));
            Mockito.when(mockResultSet.getString("OrderStatus")).thenReturn("ACTIVE");

            UserOperations.viewOrderHistory(user);

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery();
        }
    }

    @Test
    void viewOrderHistoryDisplaysNoOrdersWhenNotFound() throws SQLException {
        User user = new User("1", "John", "Doe", "john.doe@example.com", "password", "123 Main St",
                "What is your pet's name?", "Fluffy", "Credit Card", "1234567890123456",
                "123", Date.valueOf("2025-12-31"), Role.CUSTOMER);

        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(false);

            UserOperations.viewOrderHistory(user);

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery();
        }
    }

    @Test
    void viewUserProfileDisplaysUserProfileAndSubscriptions() throws SQLException {
        User user = new User("1", "John", "Doe", "john.doe@example.com", "password", "123 Main St",
                "What is your pet's name?", "Fluffy", "Credit Card", "1234567890123456",
                "123", Date.valueOf("2025-12-31"), Role.CUSTOMER);

        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true, false);
            Mockito.when(mockResultSet.getInt("SubscriptionID")).thenReturn(1);
            Mockito.when(mockResultSet.getString("SubscriptionName")).thenReturn("Subscription1");
            Mockito.when(mockResultSet.getString("SubscriptionDescription")).thenReturn("Description1");
            Mockito.when(mockResultSet.getString("SubscriptionCategory")).thenReturn("Category1");
            Mockito.when(mockResultSet.getString("SubscriptionStatus")).thenReturn("ACTIVE");
            Mockito.when(mockResultSet.getInt("SubscriptionCount")).thenReturn(1);

            UserOperations.viewUserProfile(user);

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery();
        }
    }

    @Test
    void showProductsByCategoryDisplaysProductsWhenFound() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true, false);
            Mockito.when(mockResultSet.getInt("ProductID")).thenReturn(1);
            Mockito.when(mockResultSet.getString("ProductName")).thenReturn("Product1");
            Mockito.when(mockResultSet.getBigDecimal("Price")).thenReturn(new BigDecimal("10.00"));
            Mockito.when(mockResultSet.getInt("StockCount")).thenReturn(100);
            Mockito.when(mockResultSet.getString("Description")).thenReturn("Description1");
            Mockito.when(mockResultSet.getString("Category")).thenReturn("Category1");
            Mockito.when(mockResultSet.getString("deliveryFrequency")).thenReturn("Weekly");

            UserOperations.showProductsByCategory("Category1");

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery();
        }
    }

    @Test
    void showProductsByCategoryDisplaysNoProductsWhenNotFound() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(false);

            UserOperations.showProductsByCategory("NonExistentCategory");

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery();
        }
    }

    @Test
    void viewCartDisplaysCartItemsWhenFound() throws SQLException {
        User user = new User("1", "John", "Doe", "john.doe@example.com", "password", "123 Main St",
                "What is your pet's name?", "Fluffy", "Credit Card", "1234567890123456",
                "123", Date.valueOf("2025-12-31"), Role.CUSTOMER);

        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true, false);
            Mockito.when(mockResultSet.getInt("ID")).thenReturn(1);
            Mockito.when(mockResultSet.getString("SubscriptionName")).thenReturn("Subscription1");
            Mockito.when(mockResultSet.getString("SubscriptionDescription")).thenReturn("Description1");
            Mockito.when(mockResultSet.getString("SubscriptionCategory")).thenReturn("Category1");

            UserOperations.viewCart(user);

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery();
        }
    }

    @Test
    void viewCartDisplaysEmptyCartWhenNoItemsFound() throws SQLException {
        User user = new User("1", "John", "Doe", "john.doe@example.com", "password", "123 Main St",
                "What is your pet's name?", "Fluffy", "Credit Card", "1234567890123456",
                "123", Date.valueOf("2025-12-31"), Role.CUSTOMER);

        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(false);

            UserOperations.viewCart(user);

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery();
        }
    }

    @Test
    void showDeliverablesDisplaysDeliverablesWhenFound() throws SQLException {
        User user = new User("1", "John", "Doe", "john.doe@example.com", "password", "123 Main St",
                "What is your pet's name?", "Fluffy", "Credit Card", "1234567890123456",
                "123", Date.valueOf("2025-12-31"), Role.CUSTOMER);

        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true, false);
            Mockito.when(mockResultSet.getInt("DeliveryID")).thenReturn(1);
            Mockito.when(mockResultSet.getString("ProductName")).thenReturn("Product1");
            Mockito.when(mockResultSet.getString("Description")).thenReturn("Description1");
            Mockito.when(mockResultSet.getDate("NextDeliveryDate")).thenReturn(Date.valueOf("2023-01-01"));
            Mockito.when(mockResultSet.getInt("Quantity")).thenReturn(10);
            Mockito.when(mockResultSet.getString("OrderStatus")).thenReturn("ACTIVE");

            UserOperations.showDeliverables(user);

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery();
        }
    }

    @Test
    void showDeliverablesDisplaysNoDeliverablesWhenNotFound() throws SQLException {
        User user = new User("1", "John", "Doe", "john.doe@example.com", "password", "123 Main St",
                "What is your pet's name?", "Fluffy", "Credit Card", "1234567890123456",
                "123", Date.valueOf("2025-12-31"), Role.CUSTOMER);

        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(false);

            UserOperations.showDeliverables(user);

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery();
        }
    }
}