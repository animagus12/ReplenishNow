package org.example.controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.example.controller.RegistrationOperations;
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

class RegistrationOperationsTest {

    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;

    @BeforeEach
    void setUp() throws SQLException {
        mockConnection = Mockito.mock(Connection.class);
        mockPreparedStatement = Mockito.mock(PreparedStatement.class);
    }

    @Test
    void addUserReturnsTrueWhenUserIsAddedSuccessfully() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);

            User user = new User("1", "John", "Doe", "john.doe@example.com", "password", "123 Main St",
                    "What is your pet's name?", "Fluffy", "Credit Card", "1234567890123456",
                    "123", Date.valueOf("2025-12-31"), Role.CUSTOMER);

            boolean result = RegistrationOperations.addUser(user);

            assertTrue(result);
            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeUpdate();
        }
    }

    @Test
    void addUserReturnsFalseWhenUserIsAdmin() throws SQLException {
        User user = new User("1", "John", "Doe", "john.doe@example.com", "password", "123 Main St",
                "What is your pet's name?", "Fluffy", "Credit Card", "1234567890123456",
                "123", Date.valueOf("2025-12-31"), Role.ADMIN);

        boolean result = RegistrationOperations.addUser(user);

        assertFalse(result);
        Mockito.verify(mockPreparedStatement, Mockito.never()).executeUpdate();
    }

    @Test
    void addUserThrowsSQLExceptionWhenDatabaseErrorOccurs() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.doThrow(new SQLException("Database error")).when(mockPreparedStatement).executeUpdate();

            User user = new User("1", "John", "Doe", "john.doe@example.com", "password", "123 Main St",
                    "What is your pet's name?", "Fluffy", "Credit Card", "1234567890123456",
                    "123", Date.valueOf("2025-12-31"), Role.CUSTOMER);

            assertThrows(SQLException.class, () -> RegistrationOperations.addUser(user));
        }
    }
}