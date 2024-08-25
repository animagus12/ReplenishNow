CREATE DATABASE SubscriptionECommerce;
USE SubscriptionECommerce;
CREATE TABLE BaseEntity (
    createdAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	updatedAt DATETIME NULL,
    deletedAt DATETIME NULL
);
CREATE TABLE Users (
    UserID CHAR(36) PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    emailId VARCHAR(100) NOT NULL UNIQUE,
    Password VARCHAR(255) NOT NULL,
    Address TEXT,
    securityQuestion VARCHAR(255),
    securityAnswer VARCHAR(255),
    paymentMethod ENUM('UPI', 'CARD'),
    cardNumber VARCHAR(16),
    cardCVV VARCHAR(3),
    cardExpiryDate DATE,
    role ENUM('ADMIN', 'CUSTOMER') NOT NULL
);
CREATE TABLE Products (
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    ProductName VARCHAR(100) NOT NULL,
    Price DECIMAL(10, 2) NOT NULL,
    StockCount INT NOT NULL,
    Description TEXT,
    Category VARCHAR(100),
    deliveryFrequency ENUM('WEEKLY', 'BIWEEKLY', 'MONTHLY', 'CUSTOM') NOT NULL
) ;
CREATE TABLE Subscriptions (
    SubscriptionID INT PRIMARY KEY AUTO_INCREMENT,
    SubscriptionName VARCHAR(100) NOT NULL,
    SubscriptionDescription TEXT,
    SubscriptionCategory VARCHAR(100),
    SubscriptionStatus ENUM('ACTIVE', 'INACTIVE') NOT NULL
);

CREATE TABLE SubscriptionProducts (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    SubscriptionID INT,
    ProductID INT,
    Quantity INT NOT NULL,
    FOREIGN KEY (SubscriptionID) REFERENCES Subscriptions(SubscriptionID),
    FOREIGN KEY (ProductID) REFERENCES Products(ProductID)
);

CREATE TABLE UserSubscriptions (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    UserID CHAR(36),
    SubscriptionID INT,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    NextDeliveryDate DATE NOT NULL,
    SubscriptionStatus ENUM('ACTIVE', 'INACTIVE') NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (SubscriptionID) REFERENCES Subscriptions(SubscriptionID)
);

CREATE TABLE Orders (
    OrderID INT PRIMARY KEY AUTO_INCREMENT,
    UserID CHAR(36),
    SubscriptionID INT,
    OrderDate DATE,
    StartDate DATE,
    EndDate DATE,
    OrderStatus ENUM('ACTIVE', 'INACTIVE') NOT NULL,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (SubscriptionID) REFERENCES Subscriptions(SubscriptionID)
);
CREATE TABLE ProductDeliveryDetails (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    OrderID INT,
    SubscriptionProductID INT,
    NextDeliveryDate DATE,
    FOREIGN KEY (OrderID) REFERENCES Orders(OrderID),
    FOREIGN KEY (SubscriptionProductID) REFERENCES SubscriptionProducts(ID)
);
CREATE TABLE Cart (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    UserID CHAR(36),
    SubscriptionID INT,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (SubscriptionID) REFERENCES Subscriptions(SubscriptionID)
);

-- Insert dummy data into Users table
INSERT INTO Users (UserID, FirstName, LastName, emailId, Password, Address, securityQuestion, securityAnswer, paymentMethod, cardNumber, cardCVV, cardExpiryDate, role)
VALUES 
(UUID(), 'John', 'Doe', 'johndoe1@example.com', 'password123', '123 Main St', 'What is your pet\'s name?', 'Fluffy', 'CARD', '1234567812345678', '123', '2025-12-31', 'CUSTOMER'),
(UUID(), 'Jane', 'Doe', 'janedoe1@example.com', 'password456', '456 Market St', 'What is your mother\'s maiden name?', 'Smith', 'CARD', '8765432187654321', '321', '2026-11-30', 'CUSTOMER'),
(UUID(), 'Alice', 'Smith', 'alicesmith@example.com', 'password789', '789 Elm St', 'What is your favorite color?', 'Blue', 'CARD', '2345678923456789', '234', '2027-10-31', 'CUSTOMER'),
(UUID(), 'Bob', 'Johnson', 'bobjohnson@example.com', 'password101', '321 Oak St', 'What was your first car?', 'Toyota', 'UPI', NULL, NULL, NULL, 'CUSTOMER'),
(UUID(), 'Charlie', 'Brown', 'charliebrown@example.com', 'password102', '654 Pine St', 'Where were you born?', 'New York', 'CARD', '3456789034567890', '345', '2024-09-30', 'CUSTOMER'),
(UUID(), 'David', 'Williams', 'davidwilliams@example.com', 'password103', '987 Cedar St', 'What is your father\'s middle name?', 'Michael', 'UPI', NULL, NULL, NULL, 'CUSTOMER'),
(UUID(), 'Eve', 'Taylor', 'evetaylor@example.com', 'password104', '159 Maple St', 'What was the name of your elementary school?', 'Lincoln', 'CARD', '4567890145678901', '456', '2023-08-31', 'CUSTOMER'),
(UUID(), 'Frank', 'White', 'frankwhite@example.com', 'password105', '753 Spruce St', 'What is your favorite movie?', 'Inception', 'CARD', '5678901256789012', '567', '2025-07-31', 'CUSTOMER'),
(UUID(), 'Grace', 'Harris', 'graceharris@example.com', 'password106', '852 Birch St', 'What is your favorite book?', '1984', 'UPI', NULL, NULL, NULL, 'CUSTOMER'),
(UUID(), 'Hannah', 'Miller', 'hannahmiller@example.com', 'password107', '951 Willow St', 'What was your childhood nickname?', 'Sunny', 'CARD', '6789012367890123', '678', '2026-06-30', 'CUSTOMER');

-- Insert dummy data into Products table
INSERT INTO Products (ProductName, Price, StockCount, Description, Category, deliveryFrequency)
VALUES 
('Product A', 19.99, 100, 'Description for Product A', 'Category 1', 'WEEKLY'),
('Product B', 29.99, 150, 'Description for Product B', 'Category 2', 'BIWEEKLY'),
('Product C', 39.99, 200, 'Description for Product C', 'Category 3', 'MONTHLY'),
('Product D', 49.99, 250, 'Description for Product D', 'Category 1', 'CUSTOM'),
('Product E', 59.99, 300, 'Description for Product E', 'Category 2', 'WEEKLY'),
('Product F', 69.99, 350, 'Description for Product F', 'Category 3', 'BIWEEKLY'),
('Product G', 79.99, 400, 'Description for Product G', 'Category 1', 'MONTHLY'),
('Product H', 89.99, 450, 'Description for Product H', 'Category 2', 'CUSTOM'),
('Product I', 99.99, 500, 'Description for Product I', 'Category 3', 'WEEKLY'),
('Product J', 109.99, 550, 'Description for Product J', 'Category 1', 'BIWEEKLY');

-- Insert dummy data into Subscriptions table
INSERT INTO Subscriptions (SubscriptionName, SubscriptionDescription, SubscriptionCategory, SubscriptionStatus)
VALUES 
('Subscription A', 'Description for Subscription A', 'Category 1', 'ACTIVE'),
('Subscription B', 'Description for Subscription B', 'Category 2', 'INACTIVE'),
('Subscription C', 'Description for Subscription C', 'Category 3', 'ACTIVE'),
('Subscription D', 'Description for Subscription D', 'Category 1', 'ACTIVE'),
('Subscription E', 'Description for Subscription E', 'Category 2', 'INACTIVE'),
('Subscription F', 'Description for Subscription F', 'Category 3', 'ACTIVE'),
('Subscription G', 'Description for Subscription G', 'Category 1', 'ACTIVE'),
('Subscription H', 'Description for Subscription H', 'Category 2', 'INACTIVE'),
('Subscription I', 'Description for Subscription I', 'Category 3', 'ACTIVE'),
('Subscription J', 'Description for Subscription J', 'Category 1', 'ACTIVE');

-- Insert dummy data into SubscriptionProducts table
INSERT INTO SubscriptionProducts (SubscriptionID, ProductID, Quantity)
VALUES 
(1, 1, 2),
(2, 2, 3),
(3, 3, 4),
(4, 4, 5),
(5, 5, 6),
(6, 6, 7),
(7, 7, 8),
(8, 8, 9),
(9, 9, 10),
(10, 10, 11);

-- Insert dummy data into Orders table
INSERT INTO Orders (UserID, SubscriptionID, OrderDate, StartDate, EndDate, OrderStatus)
VALUES 
((SELECT UserID FROM Users LIMIT 1 OFFSET 0), 1, '2024-01-01', '2024-01-01', '2024-12-31', 'ACTIVE'),
((SELECT UserID FROM Users LIMIT 1 OFFSET 1), 2, '2024-02-01', '2024-02-01', '2024-11-30', 'ACTIVE'),
((SELECT UserID FROM Users LIMIT 1 OFFSET 2), 3, '2024-03-01', '2024-03-01', '2024-10-31', 'ACTIVE'),
((SELECT UserID FROM Users LIMIT 1 OFFSET 3), 4, '2024-04-01', '2024-04-01', '2024-09-30', 'INACTIVE'),
((SELECT UserID FROM Users LIMIT 1 OFFSET 4), 5, '2024-05-01', '2024-05-01', '2024-08-31', 'ACTIVE'),
((SELECT UserID FROM Users LIMIT 1 OFFSET 5), 6, '2024-06-01', '2024-06-01', '2024-07-31', 'INACTIVE'),
((SELECT UserID FROM Users LIMIT 1 OFFSET 6), 7, '2024-07-01', '2024-07-01', '2024-06-30', 'ACTIVE'),
((SELECT UserID FROM Users LIMIT 1 OFFSET 7), 8, '2024-08-01', '2024-08-01', '2024-05-31', 'INACTIVE'),
((SELECT UserID FROM Users LIMIT 1 OFFSET 8), 9, '2024-09-01', '2024-09-01', '2024-04-30', 'ACTIVE'),
((SELECT UserID FROM Users LIMIT 1 OFFSET 9), 10, '2024-10-01', '2024-10-01', '2024-03-31', 'ACTIVE');

-- Insert dummy data into ProductDeliveryDetails table
INSERT INTO ProductDeliveryDetails (OrderID, SubscriptionProductID, NextDeliveryDate)
VALUES 
(1, 1, '2024-01-08'),
(2, 2, '2024-02-15'),
(3, 3, '2024-03-22'),
(4, 4, '2024-04-29'),
(5, 5, '2024-05-06'),
(6, 6, '2024-06-13'),
(7, 7, '2024-07-20'),
(8, 8, '2024-08-27'),
(9, 9, '2024-09-03'),
(10, 10, '2024-10-10');

-- Insert dummy data into Cart table
INSERT INTO Cart (UserID, SubscriptionID)
VALUES 
((SELECT UserID FROM Users LIMIT 1 OFFSET 0), 1),
((SELECT UserID FROM Users LIMIT 1 OFFSET 1), 2);




