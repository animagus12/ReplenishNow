package org.example.controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;
import org.example.controller.ProductOperations;
import org.example.config.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

class ProductOperationsTest {

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
    void subscribeProductAddsSubscriptionWhenNotAlreadySubscribed() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString(), Mockito.anyInt())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(false);

            ProductOperations.subscribeProduct("user1", 1, "Description", "Category");

            Mockito.verify(mockPreparedStatement, Mockito.times(3)).executeUpdate();
        }
    }

    @Test
    void subscribeProductDoesNotAddSubscriptionWhenAlreadySubscribed() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true);

            ProductOperations.subscribeProduct("user1", 1, "Description", "Category");

            Mockito.verify(mockPreparedStatement, Mockito.never()).executeUpdate();
        }
    }

    @Test
    void unsubscribeProductRemovesSubscriptionWhenSubscribed() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true);

            ProductOperations.unsubscribeProduct("user1", 1);

            Mockito.verify(mockPreparedStatement, Mockito.times(2)).executeUpdate();
        }
    }

    @Test
    void unsubscribeProductDoesNotRemoveSubscriptionWhenNotSubscribed() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(false);

            ProductOperations.unsubscribeProduct("user1", 1);

            Mockito.verify(mockPreparedStatement, Mockito.never()).executeUpdate();
        }
    }

    @Test
    void showSubscriptionDisplaysSubscriptionWhenSubscribed() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true);
            Mockito.when(mockResultSet.getInt("SubscriptionID")).thenReturn(1);
            Mockito.when(mockResultSet.getString("SubscriptionName")).thenReturn("SubscriptionName");
            Mockito.when(mockResultSet.getString("SubscriptionDescription")).thenReturn("SubscriptionDescription");
            Mockito.when(mockResultSet.getString("SubscriptionCategory")).thenReturn("SubscriptionCategory");
            Mockito.when(mockResultSet.getString("SubscriptionStatus")).thenReturn("ACTIVE");

            ProductOperations.showSubscription("user1", 1);

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery();
        }
    }

    @Test
    void showSubscriptionDisplaysNoSubscriptionWhenNotSubscribed() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(false);

            ProductOperations.showSubscription("user1", 1);

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeQuery();
        }
    }

    @Test
    void editSubscriptionUpdatesSubscriptionWhenSubscribed() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(true);

            ProductOperations.editSubscription("user1", 1, "NewName", "NewDescription", "NewCategory");

            Mockito.verify(mockPreparedStatement, Mockito.times(1)).executeUpdate();
        }
    }

    @Test
    void editSubscriptionDoesNotUpdateSubscriptionWhenNotSubscribed() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedDatabaseConnection = Mockito.mockStatic(DatabaseConnection.class)) {
            mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            Mockito.when(mockConnection.prepareStatement(Mockito.anyString())).thenReturn(mockPreparedStatement);
            Mockito.when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
            Mockito.when(mockResultSet.next()).thenReturn(false);

            ProductOperations.editSubscription("user1", 1, "NewName", "NewDescription", "NewCategory");

            Mockito.verify(mockPreparedStatement, Mockito.never()).executeUpdate();
        }
    }
}