package com.wiatec.panel.authorize;

import com.wiatec.panel.common.utils.UnitUtil;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;

public class AuthorizeTransactionInfo {

    public static final String TYPE_CONTRACTED = "contracted";
    public static final String TYPE_MONTHLY = "monthly";
    public static final String TYPE_RENEW = "renew";

    public static final float TAX = 0.08F;


    private int id;
    private int salesId;
    private int dealerId;
    private String salesName;
    private String category;
    private String clientKey;
    private String cardNumber;
    private String expirationDate;
    private String securityKey;

    private float amount;
    private float price;
    private float txFee;
    private float deposit;
    private float ldCommission;
    private float dealerCommission;
    private float salesCommission;

    private String transactionId;
    private String status;
    private String authCode;
    private String type;
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

    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTxFee() {
        return txFee;
    }

    public void setTxFee(float txFee) {
        this.txFee = txFee;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
                ", salesId=" + salesId +
                ", dealerId=" + dealerId +
                ", salesName='" + salesName + '\'' +
                ", category='" + category + '\'' +
                ", clientKey='" + clientKey + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", securityKey='" + securityKey + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", txFee=" + txFee +
                ", deposit=" + deposit +
                ", ldCommission=" + ldCommission +
                ", dealerCommission=" + dealerCommission +
                ", salesCommission=" + salesCommission +
                ", transactionId='" + transactionId + '\'' +
                ", status='" + status + '\'' +
                ", authCode='" + authCode + '\'' +
                ", type='" + type + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public static AuthorizeTransactionInfo contractedFromAuthRentInfo(AuthRentUserInfo authRentUserInfo){
        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
        authorizeTransactionInfo.setPrice(authRentUserInfo.getFirstPay());
        authorizeTransactionInfo.setTxFee(Float.parseFloat(UnitUtil.round(authRentUserInfo.getMonthPay() * TAX)));
        authorizeTransactionInfo.setAmount(authorizeTransactionInfo.getPrice() + authorizeTransactionInfo.getTxFee());
        authorizeTransactionInfo.setDeposit(authRentUserInfo.getDeposit());
        authorizeTransactionInfo.setLdCommission(authRentUserInfo.getLdCommission());
        authorizeTransactionInfo.setDealerCommission(authRentUserInfo.getDealerCommission());
        authorizeTransactionInfo.setSalesCommission(authRentUserInfo.getSalesCommission());
        authorizeTransactionInfo.setClientKey(authRentUserInfo.getClientKey());
        authorizeTransactionInfo.setSalesId(authRentUserInfo.getSalesId());
        authorizeTransactionInfo.setDealerId(authRentUserInfo.getDealerId());
        authorizeTransactionInfo.setCategory(authRentUserInfo.getCategory());
        authorizeTransactionInfo.setCardNumber(authRentUserInfo.getCardNumber());
        authorizeTransactionInfo.setExpirationDate(authRentUserInfo.getExpirationDate());
        authorizeTransactionInfo.setSecurityKey(authRentUserInfo.getSecurityKey());
        authorizeTransactionInfo.setType(AuthorizeTransactionInfo.TYPE_CONTRACTED);
        return authorizeTransactionInfo;
    }

    public static AuthorizeTransactionInfo monthlyFromAuthRentInfo(AuthRentUserInfo authRentUserInfo, String today){
        AuthorizeTransactionInfo authorizeTransactionInfo = new AuthorizeTransactionInfo();
        authorizeTransactionInfo.setSalesId(authRentUserInfo.getSalesId());
        authorizeTransactionInfo.setDealerId(authRentUserInfo.getDealerId());
        authorizeTransactionInfo.setClientKey(authRentUserInfo.getClientKey());
        authorizeTransactionInfo.setCategory(authRentUserInfo.getCategory());
        authorizeTransactionInfo.setCardNumber(authRentUserInfo.getCardNumber());
        authorizeTransactionInfo.setExpirationDate(authRentUserInfo.getExpirationDate());
        authorizeTransactionInfo.setSecurityKey(authRentUserInfo.getSecurityKey());
        authorizeTransactionInfo.setPrice(authRentUserInfo.getMonthPay());
        authorizeTransactionInfo.setTxFee(Float.parseFloat(UnitUtil.round(authRentUserInfo.getMonthPay() * TAX)));
        authorizeTransactionInfo.setAmount(authorizeTransactionInfo.getPrice() + authorizeTransactionInfo.getTxFee());
        authorizeTransactionInfo.setDeposit(0);
        authorizeTransactionInfo.setLdCommission(authRentUserInfo.getLdCommission());
        authorizeTransactionInfo.setDealerCommission(authRentUserInfo.getDealerCommission());
        authorizeTransactionInfo.setSalesCommission(authRentUserInfo.getSalesCommission());
        authorizeTransactionInfo.setType(AuthorizeTransactionInfo.TYPE_MONTHLY);
        authorizeTransactionInfo.setCreateTime(today.substring(0, 7));
        return authorizeTransactionInfo;
    }
}
