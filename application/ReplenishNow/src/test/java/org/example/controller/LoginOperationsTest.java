package org.example.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.example.config.DatabaseConnection;
import org.example.model.User;
import org.example.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

class LoginOperationsTest {

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
    void getUserByEmailAndPasswordReturnsUserWhenCredentialsAreCorrect() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true);
            Mockito.when(mockResultSet.getString("UserID")).thenReturn("1");
            Mockito.when(mockResultSet.getString("FirstName")).thenReturn("John");
            Mockito.when(mockResultSet.getString("LastName")).thenReturn("Doe");
            Mockito.when(mockResultSet.getString("Address")).thenReturn("123 Main St");
            Mockito.when(mockResultSet.getString("securityQuestion")).thenReturn("What is your pet's name?");
            Mockito.when(mockResultSet.getString("securityAnswer")).thenReturn("Fluffy");
            Mockito.when(mockResultSet.getString("paymentMethod")).thenReturn("Credit Card");
            Mockito.when(mockResultSet.getString("cardNumber")).thenReturn("1234567890123456");
            Mockito.when(mockResultSet.getString("cardCVV")).thenReturn("123");
            Mockito.when(mockResultSet.getDate("cardExpiryDate")).thenReturn(Date.valueOf("2025-12-31"));
            Mockito.when(mockResultSet.getString("role")).thenReturn("USER");

            User user = LoginOperations.getUserByEmailAndPassword("john.doe@example.com", "password");

            assertNotNull(user);
            assertEquals("1", user.getUserId());
            assertEquals("John", user.getFirstName());
            assertEquals("Doe", user.getLastName());
            assertEquals("123 Main St", user.getAddress());
            assertEquals("What is your pet's name?", user.getSecurityQuestion());
            assertEquals("Fluffy", user.getAnswer());
            assertEquals("Credit Card", user.getPaymentDetails());
            assertEquals("1234567890123456", user.getCardNumber());
            assertEquals("123", user.getCardCVV());
            assertEquals(Date.valueOf("2025-12-31"), user.getCardExpiryDate());
            assertEquals(Role.CUSTOMER, user.getRole());
        }
    }

    @Test
    void getUserByEmailAndPasswordReturnsNullWhenCredentialsAreIncorrect() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(false);

            User user = LoginOperations.getUserByEmailAndPassword("john.doe@example.com", "wrongpassword");

            assertNull(user);
        }
    }

    @Test
    void forgetPasswordUpdatesPasswordWhenSecurityAnswerIsCorrect() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true);
            Mockito.when(mockResultSet.getString("securityQuestion")).thenReturn("What is your pet's name?");
            Mockito.when(mockResultSet.getString("securityAnswer")).thenReturn("Fluffy");

            // Simulate user input
            try (MockedStatic<System> mockedSystem = Mockito.mockStatic(System.class)) {
                mockedSystem.when(() -> System.console().readLine()).thenReturn("Fluffy", "newpassword");

                LoginOperations.forgetPassword("john.doe@example.com");

                Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeUpdate();
            }
        }
    }

    @Test
    void forgetPasswordDoesNotUpdatePasswordWhenSecurityAnswerIsIncorrect() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true);
            Mockito.when(mockResultSet.getString("securityQuestion")).thenReturn("What is your pet's name?");
            Mockito.when(mockResultSet.getString("securityAnswer")).thenReturn("Fluffy");

            // Simulate user input
            try (MockedStatic<System> mockedSystem = Mockito.mockStatic(System.class)) {
                mockedSystem.when(() -> System.console().readLine()).thenReturn("WrongAnswer");

                LoginOperations.forgetPassword("john.doe@example.com");

                Mockito.verify(mockPreparedStatement, Mockito.never()).executeUpdate();
            }
        }
    }
}