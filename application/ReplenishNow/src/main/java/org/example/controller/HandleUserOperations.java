package org.example.controller;

import org.example.model.User;

import java.sql.SQLException;
import java.util.Scanner;

public class HandleUserOperations {
    public static void handleUserOperations(User user) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Select an operation:");
            System.out.println("1. Search Product by Name");
            System.out.println("2. Show All Products");
            System.out.println("3. View Order History");
            System.out.println("4. View User Profile");
            System.out.println("5. Show Products by Category");
            System.out.println("6. Show All Subscriptions");
            System.out.println("7. Subscriptions");
            System.out.println("8. Show Deliverables");
            System.out.println("9. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter product name to search: ");
                        String productName = scanner.nextLine();
                        UserOperations.searchProductByName(productName);
                        break;

                    case 2:
                        UserOperations.showAllProducts();
                        break;

                    case 3:
                        UserOperations.viewOrderHistory(user);
                        break;

                    case 4:
                        UserOperations.viewUserProfile(user);
                        break;

                    case 5:
                        System.out.print("Enter category to filter products: ");
                        String category = scanner.nextLine();
                        UserOperations.showProductsByCategory(category);
                        break;

                    case 6:
                        UserOperations.viewCart(user);
                        break;

                    case 7:
                        UserOperations.subscriptions(user);
                        break;

                    case 8:
                        UserOperations.showDeliverables(user);
                        break;
                        
                    case 9:
                        System.out.println("Exiting...");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (SQLException e) {
                System.out.println("An error occurred while performing the operation: " + e.getMessage());
            }

            System.out.println();
        }
    }
}
