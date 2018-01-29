package com.wiatec.panel.authorize;

import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.SalesActivateCategoryInfo;
import com.wiatec.panel.oxm.pojo.SalesGoldCategoryInfo;

/**
 * @author patrick
 */
public class AuthorizeTransactionSalesInfo {

    public static final String TYPE_NORMAL = "normal";
    public static final String TYPE_GOLD_S1 = "s1";



    private int id;
    private int salesId;
    private String salesName;
    private String cardNumber;
    private String expirationDate;
    private String securityKey;

    private float amount;
    private String activateCategory;
    private float activatePay;
    private String goldCategory;
    private float goldPay;

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

    public String getActivateCategory() {
        return activateCategory;
    }

    public void setActivateCategory(String activateCategory) {
        this.activateCategory = activateCategory;
    }

    public float getActivatePay() {
        return activatePay;
    }

    public void setActivatePay(float activatePay) {
        this.activatePay = activatePay;
    }

    public String getGoldCategory() {
        return goldCategory;
    }

    public void setGoldCategory(String goldCategory) {
        this.goldCategory = goldCategory;
    }

    public float getGoldPay() {
        return goldPay;
    }

    public void setGoldPay(float goldPay) {
        this.goldPay = goldPay;
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
                ", activateCategory='" + activateCategory + '\'' +
                ", activatePay=" + activatePay +
                ", goldCategory='" + goldCategory + '\'' +
                ", goldPay=" + goldPay +
                ", transactionId='" + transactionId + '\'' +
                ", status='" + status + '\'' +
                ", authCode='" + authCode + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }

    public static AuthorizeTransactionSalesInfo createNormal(AuthSalesInfo authSalesInfo,
                                                             SalesActivateCategoryInfo salesActivateCategoryInfo){
        AuthorizeTransactionSalesInfo authorizeTransactionSalesInfo = new AuthorizeTransactionSalesInfo();
        authorizeTransactionSalesInfo.setSalesId(authSalesInfo.getId());
        authorizeTransactionSalesInfo.setCardNumber(authSalesInfo.getCardNumber());
        authorizeTransactionSalesInfo.setExpirationDate(authSalesInfo.getExpirationDate());
        authorizeTransactionSalesInfo.setSecurityKey(authSalesInfo.getSecurityKey());
        authorizeTransactionSalesInfo.setActivateCategory(authSalesInfo.getActivateCategory());
        authorizeTransactionSalesInfo.setActivatePay(salesActivateCategoryInfo.getPrice());
        authorizeTransactionSalesInfo.setGoldCategory("no");
        authorizeTransactionSalesInfo.setGoldPay(0F);
        authorizeTransactionSalesInfo.setAmount(salesActivateCategoryInfo.getPrice());
        return authorizeTransactionSalesInfo;
    }

    public static AuthorizeTransactionSalesInfo createGold(AuthSalesInfo authSalesInfo,
                                                           SalesActivateCategoryInfo salesActivateCategoryInfo,
                                                           SalesGoldCategoryInfo salesGoldCategoryInfo){
        AuthorizeTransactionSalesInfo authorizeTransactionSalesInfo = new AuthorizeTransactionSalesInfo();
        authorizeTransactionSalesInfo.setSalesId(authSalesInfo.getId());
        authorizeTransactionSalesInfo.setCardNumber(authSalesInfo.getCardNumber());
        authorizeTransactionSalesInfo.setExpirationDate(authSalesInfo.getExpirationDate());
        authorizeTransactionSalesInfo.setSecurityKey(authSalesInfo.getSecurityKey());
        authorizeTransactionSalesInfo.setActivateCategory(authSalesInfo.getActivateCategory());
        authorizeTransactionSalesInfo.setActivatePay(salesActivateCategoryInfo.getPrice());
        authorizeTransactionSalesInfo.setGoldCategory(authSalesInfo.getGoldCategory());
        authorizeTransactionSalesInfo.setGoldPay(salesGoldCategoryInfo.getAmount());
        authorizeTransactionSalesInfo.setAmount(salesActivateCategoryInfo.getPrice() + salesGoldCategoryInfo.getAmount());
        return authorizeTransactionSalesInfo;
    }
}
