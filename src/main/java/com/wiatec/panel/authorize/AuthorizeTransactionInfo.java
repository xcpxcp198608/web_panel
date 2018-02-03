package com.wiatec.panel.authorize;

import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.AuthSalesInfo;

/**
 * @author patrick
 */
public class AuthorizeTransactionInfo {

    public static final float TAX = 0.08F;

    private int id;
    private String cardNumber;
    private String expirationDate;
    private String securityKey;
    private float amount;

    private String transactionId;
    private String status;
    private String authCode;
    private String createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "AuthorizeTransactionInfo{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", securityKey='" + securityKey + '\'' +
                ", amount=" + amount +
                ", transactionId='" + transactionId + '\'' +
                ", status='" + status + '\'' +
                ", authCode='" + authCode + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public static AuthorizeTransactionInfo createFromAuthSales(AuthSalesInfo authSalesInfo, float amount){
        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
        authorizeTransactionInfo.setCardNumber(authSalesInfo.getCardNumber());
        authorizeTransactionInfo.setExpirationDate(authSalesInfo.getExpirationDate());
        authorizeTransactionInfo.setSecurityKey(authSalesInfo.getSecurityKey());
        authorizeTransactionInfo.setAmount(amount);
        return authorizeTransactionInfo;
    }

    public static AuthorizeTransactionInfo createFromAuthRentUser(AuthRentUserInfo authRentUserInfo, float amount){
        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
        authorizeTransactionInfo.setCardNumber(authRentUserInfo.getCardNumber());
        authorizeTransactionInfo.setExpirationDate(authRentUserInfo.getExpirationDate());
        authorizeTransactionInfo.setSecurityKey(authRentUserInfo.getSecurityKey());
        authorizeTransactionInfo.setAmount(amount);
        return authorizeTransactionInfo;
    }
}
