package com.wiatec.panel.authorize;

import com.wiatec.panel.common.utils.UnitUtil;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;

/**
 * @author patrick
 */
public class AuthorizeTransactionRentalInfo {

    public static final String TYPE_CONTRACTED = "rentalContracted";
    public static final String TYPE_MONTHLY = "rentalMonthly";
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
    private float ldeCommission;
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

    public float getLdeCommission() {
        return ldeCommission;
    }

    public void setLdeCommission(float ldeCommission) {
        this.ldeCommission = ldeCommission;
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
        return "AuthorizeTransactionRentalInfo{" +
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
                ", ldeCommission=" + ldeCommission +
                ", dealerCommission=" + dealerCommission +
                ", salesCommission=" + salesCommission +
                ", transactionId='" + transactionId + '\'' +
                ", status='" + status + '\'' +
                ", authCode='" + authCode + '\'' +
                ", type='" + type + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public static AuthorizeTransactionRentalInfo contractedFromAuthRentInfo(AuthRentUserInfo authRentUserInfo){
        AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo = new AuthorizeTransactionRentalInfo();
        authorizeTransactionRentalInfo.setPrice(authRentUserInfo.getFirstPay());
        authorizeTransactionRentalInfo.setTxFee(Float.parseFloat(UnitUtil.round(authRentUserInfo.getMonthPay() * TAX)));
        authorizeTransactionRentalInfo.setAmount(authorizeTransactionRentalInfo.getPrice() + authorizeTransactionRentalInfo.getTxFee());
        authorizeTransactionRentalInfo.setDeposit(authRentUserInfo.getDeposit());
        authorizeTransactionRentalInfo.setLdCommission(authRentUserInfo.getLdCommission());
        authorizeTransactionRentalInfo.setLdeCommission(authRentUserInfo.getLdeCommission());
        authorizeTransactionRentalInfo.setDealerCommission(authRentUserInfo.getDealerCommission());
        authorizeTransactionRentalInfo.setSalesCommission(authRentUserInfo.getSalesCommission());
        authorizeTransactionRentalInfo.setClientKey(authRentUserInfo.getClientKey());
        authorizeTransactionRentalInfo.setSalesId(authRentUserInfo.getSalesId());
        authorizeTransactionRentalInfo.setDealerId(authRentUserInfo.getDealerId());
        authorizeTransactionRentalInfo.setCategory(authRentUserInfo.getCategory());
        authorizeTransactionRentalInfo.setCardNumber(authRentUserInfo.getCardNumber());
        authorizeTransactionRentalInfo.setExpirationDate(authRentUserInfo.getExpirationDate());
        authorizeTransactionRentalInfo.setSecurityKey(authRentUserInfo.getSecurityKey());
        authorizeTransactionRentalInfo.setType(AuthorizeTransactionRentalInfo.TYPE_CONTRACTED);
        return authorizeTransactionRentalInfo;
    }

    public static AuthorizeTransactionRentalInfo monthlyFromAuthRentInfo(AuthRentUserInfo authRentUserInfo, String today){
        AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo = new AuthorizeTransactionRentalInfo();
        authorizeTransactionRentalInfo.setSalesId(authRentUserInfo.getSalesId());
        authorizeTransactionRentalInfo.setDealerId(authRentUserInfo.getDealerId());
        authorizeTransactionRentalInfo.setClientKey(authRentUserInfo.getClientKey());
        authorizeTransactionRentalInfo.setCategory(authRentUserInfo.getCategory());
        authorizeTransactionRentalInfo.setCardNumber(authRentUserInfo.getCardNumber());
        authorizeTransactionRentalInfo.setExpirationDate(authRentUserInfo.getExpirationDate());
        authorizeTransactionRentalInfo.setSecurityKey(authRentUserInfo.getSecurityKey());
        authorizeTransactionRentalInfo.setPrice(authRentUserInfo.getMonthPay());
        authorizeTransactionRentalInfo.setTxFee(Float.parseFloat(UnitUtil.round(authRentUserInfo.getMonthPay() * TAX)));
        authorizeTransactionRentalInfo.setAmount(authorizeTransactionRentalInfo.getPrice() + authorizeTransactionRentalInfo.getTxFee());
        authorizeTransactionRentalInfo.setDeposit(0);
        authorizeTransactionRentalInfo.setLdCommission(authRentUserInfo.getLdCommission());
        authorizeTransactionRentalInfo.setLdeCommission(authRentUserInfo.getLdeCommission());
        authorizeTransactionRentalInfo.setDealerCommission(authRentUserInfo.getDealerCommission());
        authorizeTransactionRentalInfo.setSalesCommission(authRentUserInfo.getSalesCommission());
        authorizeTransactionRentalInfo.setType(AuthorizeTransactionRentalInfo.TYPE_MONTHLY);
        authorizeTransactionRentalInfo.setCreateTime(today.substring(0, 7));
        return authorizeTransactionRentalInfo;
    }
}
