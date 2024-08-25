Tables
Users
- Admin
- Customer
  Product
  Subscriptions
  Orders
  Order History

Base Entity
- createdAt: Date
- updatedAt: Date
- deletedAt: Date

Users
- UserID PK ---- use guid if opossible
- FirstName
- LastName
- emailId
- Password(hashing)
- Address
- securityQuestion and Answer
- Enum(upi/card) -card(number, cvv, date)
- Roles Enum - Admin, Customer

Product
- ProductID(Auto increment) PK
- Product Name
- Price
- stock count
- description
- category
- deliveryFrequency // Enum(weekly, biweekly, monthly, custom) // Map in backend

Cart
- id - default
- userID - FK
- subscriptionID - FK

Subscriptions
- SubscriptionsID(Auto Increment) PK
- subscriptionName
- subscriptionDescription
- subscriptionCategory
- subscriptionStatus // Enum(active, inactive) 
- subscriptionCount(if implementing Featured Subs) default = 0

SubscriptionProducts
- Id - default
- SubscriptionId - Fk
- ProductId - FK
- quantity

Orders
- orderID PK
- userId FK
- subscriptionID FK
- order date
- start date
- end date
- orderStatus // Enum(active, inactive)

ProductDeliveryDetails
- id - default
- orderId - FK
  SubscriptionProducts - FK
- nextDeliveryDate - Date



