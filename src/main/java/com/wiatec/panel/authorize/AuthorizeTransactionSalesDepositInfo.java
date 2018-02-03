package com.wiatec.panel.authorize;

import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;
import com.wiatec.panel.oxm.pojo.SalesActivateCategoryInfo;
import com.wiatec.panel.oxm.pojo.SalesGoldCategoryInfo;

/**
 * @author patrick
 */
public class AuthorizeTransactionSalesDepositInfo {

    private int id;
    private int salesId;
    private String salesName;
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

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
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
        return "AuthorizeTransactionSalesInfo{" +
                "id=" + id +
                ", salesId=" + salesId +
                ", salesName='" + salesName + '\'' +
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

    public static AuthorizeTransactionSalesDepositInfo createDepositForRepStore(AuthSalesInfo authSalesInfo, AuthorizeTransactionInfo authorizeTransactionInfo){
        AuthorizeTransactionSalesDepositInfo authorizeTransactionSalesDepositInfo = new AuthorizeTransactionSalesDepositInfo();
        authorizeTransactionSalesDepositInfo.setSalesId(authSalesInfo.getId());

        authorizeTransactionSalesDepositInfo.setCardNumber(authorizeTransactionInfo.getCardNumber());
        authorizeTransactionSalesDepositInfo.setExpirationDate(authorizeTransactionInfo.getExpirationDate());
        authorizeTransactionSalesDepositInfo.setSecurityKey(authorizeTransactionInfo.getSecurityKey());
        authorizeTransactionSalesDepositInfo.setAmount(authorizeTransactionInfo.getAmount());
        authorizeTransactionSalesDepositInfo.setTransactionId(authorizeTransactionInfo.getTransactionId());
        authorizeTransactionSalesDepositInfo.setAuthCode(authorizeTransactionInfo.getAuthCode());
        authorizeTransactionSalesDepositInfo.setStatus(authorizeTransactionInfo.getStatus());
        return authorizeTransactionSalesDepositInfo;
    }
}
