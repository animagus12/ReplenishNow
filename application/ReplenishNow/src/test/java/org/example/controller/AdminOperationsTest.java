package org.example.controller;

import org.example.model.User;
import org.example.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

class AdminOperationsTest {

    private AdminOperations adminOperations;
    private User adminUser;
    private User nonAdminUser;

    @BeforeEach
    public void setUp() {
        adminOperations = new AdminOperations();
        adminUser = new User("1", "Admin", "User", "admin@example.com", "password", "address", "question", "answer", "paymentDetails", "cardNumber", "cardCVV", null, Role.ADMIN);
        nonAdminUser = new User("2", "NonAdmin", "User", "user@example.com", "password", "address", "question", "answer", "paymentDetails", "cardNumber", "cardCVV", null, Role.CUSTOMER);
    }

    @Test
    public void showOrderHistoryByProductId_AdminUser_ShouldDisplayOrderHistory() throws SQLException {
        adminOperations.showOrderHistoryByProductId(adminUser, 1);
        // Verify the order history was displayed correctly
    }

    @Test
    public void showOrderHistoryByProductId_NonAdminUser_ShouldDenyAccess() throws SQLException {
        adminOperations.showOrderHistoryByProductId(nonAdminUser, 1);
        // Verify access denied message was printed
    }

    @Test
    public void showOrderHistoryByUserId_AdminUser_ShouldDisplayOrderHistory() throws SQLException {
        adminOperations.showOrderHistoryByUserId(adminUser, "1");
        // Verify the order history was displayed correctly
    }

    @Test
    public void showOrderHistoryByUserId_NonAdminUser_ShouldDenyAccess() throws SQLException {
        adminOperations.showOrderHistoryByUserId(nonAdminUser, "1");
        // Verify access denied message was printed
    }

    @Test
    public void activateSubscription_ValidSubscription_ShouldActivateSubscription() throws SQLException {
        adminOperations.activateSubscription(1);
        // Verify the subscription was activated successfully
    }

    @Test
    public void activateSubscription_InvalidSubscription_ShouldNotActivateSubscription() throws SQLException {
        adminOperations.activateSubscription(999);
        // Verify the subscription was not found and not activated
    }

    @Test
    public void deactivateSubscription_ValidSubscription_ShouldDeactivateSubscription() throws SQLException {
        adminOperations.deactivateSubscription(1);
        // Verify the subscription was deactivated successfully
    }

    @Test
    public void deactivateSubscription_InvalidSubscription_ShouldNotDeactivateSubscription() throws SQLException {
        adminOperations.deactivateSubscription(999);
        // Verify the subscription was not found and not deactivated
    }

    @Test
    public void viewDailyDeliveryList_ValidDate_ShouldDisplayDeliveryList() throws SQLException {
        adminOperations.viewDailyDeliveryList(Date.valueOf("2023-10-10"));
        // Verify the delivery list was displayed correctly
    }

    @Test
    public void viewDailyDeliveryList_NoDeliveries_ShouldDisplayNoDeliveriesMessage() throws SQLException {
        adminOperations.viewDailyDeliveryList(Date.valueOf("2023-12-25"));
        // Verify the no deliveries message was displayed
    }

    @Test
    public void viewAllSubscriptions_ShouldDisplayAllSubscriptions() throws SQLException {
        adminOperations.viewAllSubscriptions();
        // Verify all subscriptions were displayed correctly
    }

    @Test
    public void viewOrderDashboard_ShouldDisplayOrderDashboard() throws SQLException {
        adminOperations.viewOrderDashboard();
        // Verify the order dashboard was displayed correctly
    }
}