package org.example.model;

public class Product {
    private int productId;
    private String productName;
    private double price;
    private int stockCount;
    private String description;
    private String category;
    private String deliveryFrequency;

    public Product(int productId, String productName, double price, int stockCount, String description, String category, String deliveryFrequency) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.stockCount = stockCount;
        this.description = description;
        this.category = category;
        this.deliveryFrequency = deliveryFrequency;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockCount() {
        return stockCount;
    }

    public void setStockCount(int stockCount) {
        this.stockCount = stockCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeliveryFrequency() {
        return deliveryFrequency;
    }

    public void setDeliveryFrequency(String deliveryFrequency) {
        this.deliveryFrequency = deliveryFrequency;
    }
}
