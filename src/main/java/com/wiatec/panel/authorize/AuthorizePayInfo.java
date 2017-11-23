package com.wiatec.panel.authorize;

public class AuthorizePayInfo {

    private int id;
    private String clientKey;
    private String category;
    private int salesId;
    private float amount;
    private String cardNumber;
    private int expirationDate;
    private int securityKey;

    private String transactionId;
    private String status;
    private String authCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(int expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(int securityKey) {
        this.securityKey = securityKey;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Override
    public String toString() {
        return "AuthorizePayInfo{" +
                "id=" + id +
                ", clientKey='" + clientKey + '\'' +
                ", category='" + category + '\'' +
                ", salesId=" + salesId +
                ", transactionId='" + transactionId + '\'' +
                ", amount=" + amount +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate=" + expirationDate +
                ", securityKey=" + securityKey +
                ", status='" + status + '\'' +
                ", authCode='" + authCode + '\'' +
                '}';
    }
}
