package com.wiatec.panel.oxm.pojo;

public class AuthOrderInfo {

    private int id;
    private int salesId;
    private String payClientKey;
    private String category;
    private String paymentId;
    private float price;
    private float deposit;
    private float ldCommission;
    private float dealerCommission;
    private float salesCommission;
    private String status;
    private String description;
    private String tradingTime;

    private AuthSalesInfo authSalesInfo;
    private AuthRentUserInfo authRentUserInfo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getPayClientKey() {
        return payClientKey;
    }

    public void setPayClientKey(String payClientKey) {
        this.payClientKey = payClientKey;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public float getLdCommission() {
        return ldCommission;
    }

    public void setLdCommission(float ldCommission) {
        this.ldCommission = ldCommission;
    }

    public float getDealerCommission() {
        return dealerCommission;
    }

    public void setDealerCommission(float dealerCommission) {
        this.dealerCommission = dealerCommission;
    }

    public float getSalesCommission() {
        return salesCommission;
    }

    public void setSalesCommission(float salesCommission) {
        this.salesCommission = salesCommission;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTradingTime() {
        return tradingTime;
    }

    public void setTradingTime(String tradingTime) {
        this.tradingTime = tradingTime;
    }

    public AuthSalesInfo getAuthSalesInfo() {
        return authSalesInfo;
    }

    public void setAuthSalesInfo(AuthSalesInfo authSalesInfo) {
        this.authSalesInfo = authSalesInfo;
    }

    public AuthRentUserInfo getAuthRentUserInfo() {
        return authRentUserInfo;
    }

    public void setAuthRentUserInfo(AuthRentUserInfo authRentUserInfo) {
        this.authRentUserInfo = authRentUserInfo;
    }

    @Override
    public String toString() {
        return "AuthOrderInfo{" +
                "id=" + id +
                ", salesId=" + salesId +
                ", payClientKey='" + payClientKey + '\'' +
                ", category='" + category + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", price=" + price +
                ", deposit=" + deposit +
                ", ldCommission=" + ldCommission +
                ", dealerCommission=" + dealerCommission +
                ", salesCommission=" + salesCommission +
                ", status='" + status + '\'' +
                ", description='" + description + '\'' +
                ", tradingTime='" + tradingTime + '\'' +
                ", authSalesInfo=" + authSalesInfo +
                ", authRentUserInfo=" + authRentUserInfo +
                '}';
    }
}
