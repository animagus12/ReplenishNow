package org.example.validator;

import org.example.model.Role;
import org.example.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

    @Test
    public void isAdmin_UserWithAdminRole_ShouldReturnTrue() {
        User adminUser = new User("1", "Admin", "User", "admin@example.com", "password", "address", "question", "answer", "paymentDetails", "cardNumber", "cardCVV", null, Role.ADMIN);
        assertTrue(UserValidator.isAdmin(adminUser));
    }

    @Test
    public void isAdmin_UserWithCustomerRole_ShouldReturnFalse() {
        User customerUser = new User("2", "Customer", "User", "customer@example.com", "password", "address", "question", "answer", "paymentDetails", "cardNumber", "cardCVV", null, Role.CUSTOMER);
        assertFalse(UserValidator.isAdmin(customerUser));
    }

    @Test
    public void isAdmin_UserWithNullRole_ShouldReturnFalse() {
        User nullRoleUser = new User("3", "Null", "Role", "null@example.com", "password", "address", "question", "answer", "paymentDetails", "cardNumber", "cardCVV", null, null);
        assertFalse(UserValidator.isAdmin(nullRoleUser));
    }

    @Test
    public void isCustomer_UserWithCustomerRole_ShouldReturnTrue() {
        User customerUser = new User("2", "Customer", "User", "customer@example.com", "password", "address", "question", "answer", "paymentDetails", "cardNumber", "cardCVV", null, Role.CUSTOMER);
        assertTrue(UserValidator.isCustomer(customerUser));
    }

    @Test
    public void isCustomer_UserWithAdminRole_ShouldReturnFalse() {
        User adminUser = new User("1", "Admin", "User", "admin@example.com", "password", "address", "question", "answer", "paymentDetails", "cardNumber", "cardCVV", null, Role.ADMIN);
        assertFalse(UserValidator.isCustomer(adminUser));
    }

    @Test
    public void isCustomer_UserWithNullRole_ShouldReturnFalse() {
        User nullRoleUser = new User("3", "Null", "Role", "null@example.com", "password", "address", "question", "answer", "paymentDetails", "cardNumber", "cardCVV", null, null);
        assertFalse(UserValidator.isCustomer(nullRoleUser));
    }
}