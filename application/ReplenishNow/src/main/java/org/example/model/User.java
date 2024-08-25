package org.example.model;

import java.util.Date;

public class User {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String address;
    private String securityQuestion;
    private String answer;
    private String paymentDetails;
    private String cardNumber;
    private String cardCVV;
    private Date cardExpiryDate;
    private Role role;

    public User(String userId, String firstName, String lastName, String emailId, String password, String address, String securityQuestion, String answer, String paymentDetails, String cardNumber, String cardCVV, java.sql.Date cardExpiryDate, Role role)
    {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = emailId;
        this.password = password;
        this.address = address;
        this.securityQuestion = securityQuestion;
        this.answer = answer;
        this.paymentDetails = paymentDetails;
        this.cardNumber = cardNumber;
        this.cardCVV = cardCVV;
        this.cardExpiryDate = cardExpiryDate;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardCVV() {
        return cardCVV;
    }

    public void setCardCVV(String cardCVV) {
        this.cardCVV = cardCVV;
    }

    public Date getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(Date cardExpiryDate) {
        this.cardExpiryDate = cardExpiryDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

