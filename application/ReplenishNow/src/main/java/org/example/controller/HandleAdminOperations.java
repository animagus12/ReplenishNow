package org.example.controller;

import org.example.model.User;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class HandleAdminOperations {
    public static void handleAdminOperations(User adminUser) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Select an operation:");
            System.out.println("1. Show All Products");
            System.out.println("2. Add New Product");
            System.out.println("3. Edit Existing Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Search Product by ID");
            System.out.println("6. Show All Order History");
            System.out.println("7. Show Order History by Product ID");
            System.out.println("8. Show Order History by User ID");
            System.out.println("9. Activate Subscription");
            System.out.println("10. Deactivate Subscription");
            System.out.println("11. View Daily Delivery List");
            System.out.println("12. View All Subscriptions");
            System.out.println("13. View Order Dashboard");
            System.out.println("14. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            try {
                switch (choice) {
                    case 1:
                        AdminOperations.showAllProducts(adminUser);
                        break;

                    case 2:
                        System.out.print("Enter Product Name: ");
                        String productName = scanner.nextLine();
                        System.out.print("Enter Price: ");
                        double price = scanner.nextDouble();
                        System.out.print("Enter Stock Count: ");
                        int stockCount = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter Description: ");
                        String description = scanner.nextLine();
                        System.out.print("Enter Category: ");
                        String category = scanner.nextLine();
                        System.out.print("Enter Delivery Frequency: ");
                        String deliveryFrequency = scanner.nextLine();

                        AdminOperations.addProduct(adminUser, productName, price, stockCount, description, category, deliveryFrequency);
                        break;

                    case 3:
                        System.out.print("Enter Product ID to Edit: ");
                        int productId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter New Product Name: ");
                        productName = scanner.nextLine();
                        System.out.print("Enter New Price: ");
                        price = scanner.nextDouble();
                        System.out.print("Enter New Stock Count: ");
                        stockCount = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter New Description: ");
                        description = scanner.nextLine();
                        System.out.print("Enter New Category: ");
                        category = scanner.nextLine();
                        System.out.print("Enter New Delivery Frequency: ");
                        deliveryFrequency = scanner.nextLine();

                        AdminOperations.editProduct(adminUser, productId, productName, price, stockCount, description, category, deliveryFrequency);
                        break;

                    case 4:
                        System.out.print("Enter Product ID to Delete: ");
                        productId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        AdminOperations.deleteProduct(adminUser, productId);
                        break;

                    case 5:
                        System.out.print("Enter Product ID to Search: ");
                        productId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        AdminOperations.searchProductById(adminUser, productId);
                        break;

                    case 6:
                        AdminOperations.showAllOrderHistory(adminUser);
                        break;

                    case 7:
                        System.out.print("Enter Product ID to View Order History: ");
                        productId = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        AdminOperations.showOrderHistoryByProductId(adminUser, productId);
                        break;

                    case 8:
                        System.out.print("Enter User ID to View Order History: ");
                        String userId = scanner.nextLine();

                        AdminOperations.showOrderHistoryByUserId(adminUser, userId);
                        break;

                    case 9:
                        System.out.print("Enter Subscription ID to Activate: ");
                        int subscriptionId2 = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        AdminOperations.activateSubscription(subscriptionId2);
                        break;

                    case 10:
                        System.out.println("Enter Subscription ID to Deactivate: ");
                        int subscriptionId3 = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        AdminOperations.deactivateSubscription(subscriptionId3);
                        break;

                    case 11:
                        System.out.println("Enter date in format yyyy-MM-dd: ");
                        String date = scanner.nextLine();
                        Date myDate = Date.valueOf(date);
                        AdminOperations.viewDailyDeliveryList(myDate);
                        break;

                    case 12:
                        AdminOperations.viewAllSubscriptions();
                        break;

                    case 13:
                        AdminOperations.viewOrderDashboard();
                        break;

                    case 14:
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
