package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
   private static Connection connection;
   public static Connection getConnection() {
      // code to create a connection
       try {
           String URL = "jdbc:mysql://localhost:3306/subscriptionecommerce";
           String USER = "root";
           String PASSWORD = "Sanskar1001";

           connection = DriverManager.getConnection(URL, USER, PASSWORD);

       } catch (Exception e) {
           e.printStackTrace();
       }

       return connection;
   }
}
