package org.example.validator;

import org.example.model.User;

public class UserValidator {

    public static boolean isAdmin(User user) {
        if(user.getRole().toString().equals("ADMIN")) {
            return true;
        }
        return false;
    }

    public static boolean isCustomer(User user) {
        if(user.getRole().toString().equals("CUSTOMER")) {
            return true;
        }
        return false;
    }
}

