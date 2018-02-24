package com.wiatec.panel.authorize;

import com.wiatec.panel.common.utils.UnitUtil;
import com.wiatec.panel.oxm.pojo.AuthRentUserInfo;
import com.wiatec.panel.oxm.pojo.CommissionCategoryInfo;

/**
 * @author patrick
 */
public class AuthorizeTransactionRentalInfo extends AuthorizeTransactionInfo {

    public static final String TYPE_CONTRACTED = "contracted";
    public static final String TYPE_MONTHLY = "monthly";
    public static final String TYPE_RENEW = "renew";

    public static final float TAX = 0.08F;

    private int salesId;
    private int dealerId;
    private String salesName;
    private String dealerName;
    private String category;
    private String clientKey;
    private String type;
    private float price;
    private float txFee;
    private float deposit;
    private float ldCommission;
    private float ldeCommission;
    private float dealerCommission;
    private float salesCommission;
    private float svcCharge;
    private float activatePay;
    private float ldActivationComm;
    private float ldeActivationComm;
    private float dealerActivationComm;
    private float salesActivationComm;


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

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
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


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getSvcCharge() {
        return svcCharge;
    }

    public void setSvcCharge(float svcCharge) {
        this.svcCharge = svcCharge;
    }

    public float getActivatePay() {
        return activatePay;
    }

    public void setActivatePay(float activatePay) {
        this.activatePay = activatePay;
    }

    public float getLdActivationComm() {
        return ldActivationComm;
    }

    public void setLdActivationComm(float ldActivationComm) {
        this.ldActivationComm = ldActivationComm;
    }

    public float getLdeActivationComm() {
        return ldeActivationComm;
    }

    public void setLdeActivationComm(float ldeActivationComm) {
        this.ldeActivationComm = ldeActivationComm;
    }

    public float getDealerActivationComm() {
        return dealerActivationComm;
    }

    public void setDealerActivationComm(float dealerActivationComm) {
        this.dealerActivationComm = dealerActivationComm;
    }

    public float getSalesActivationComm() {
        return salesActivationComm;
    }

    public void setSalesActivationComm(float salesActivationComm) {
        this.salesActivationComm = salesActivationComm;
    }

    @Override
    public String toString() {
        return "AuthorizeTransactionRentalInfo{" +
                "salesId=" + salesId +
                ", dealerId=" + dealerId +
                ", salesName='" + salesName + '\'' +
                ", category='" + category + '\'' +
                ", clientKey='" + clientKey + '\'' +
                ", price=" + price +
                ", txFee=" + txFee +
                ", deposit=" + deposit +
                ", ldCommission=" + ldCommission +
                ", ldeCommission=" + ldeCommission +
                ", dealerCommission=" + dealerCommission +
                ", salesCommission=" + salesCommission +
                ", type='" + type + '\'' +
                '}';
    }

    public static AuthorizeTransactionRentalInfo contractedFromAuthRentInfo(AuthRentUserInfo authRentUserInfo,
                                                                            CommissionCategoryInfo commissionCategoryInfo,
                                                                            AuthorizeTransactionInfo authorizeTransactionInfo){
        AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo = new AuthorizeTransactionRentalInfo();

        authorizeTransactionRentalInfo.setClientKey(authRentUserInfo.getClientKey());
        authorizeTransactionRentalInfo.setSalesId(authRentUserInfo.getSalesId());
        authorizeTransactionRentalInfo.setDealerId(authRentUserInfo.getDealerId());
        authorizeTransactionRentalInfo.setCategory(authRentUserInfo.getCategory());
        authorizeTransactionRentalInfo.setDeposit(authRentUserInfo.getDeposit());
        authorizeTransactionRentalInfo.setLdCommission(authRentUserInfo.getLdCommission());
        authorizeTransactionRentalInfo.setLdeCommission(authRentUserInfo.getLdeCommission());
        authorizeTransactionRentalInfo.setDealerCommission(authRentUserInfo.getDealerCommission());
        authorizeTransactionRentalInfo.setSalesCommission(authRentUserInfo.getSalesCommission());
        authorizeTransactionRentalInfo.setSvcCharge(authRentUserInfo.getSvcCharge());

        authorizeTransactionRentalInfo.setPrice(commissionCategoryInfo.getFirstPay());
        authorizeTransactionRentalInfo.setTxFee(Float.parseFloat(UnitUtil
                .round(commissionCategoryInfo.getFirstPay() * TAX)));
        authorizeTransactionRentalInfo.setActivatePay(commissionCategoryInfo.getActivatePay());
        authorizeTransactionRentalInfo.setLdActivationComm(commissionCategoryInfo.getLdActivationComm());
        authorizeTransactionRentalInfo.setLdeActivationComm(commissionCategoryInfo.getLdeActivationComm());
        authorizeTransactionRentalInfo.setDealerActivationComm(commissionCategoryInfo.getDealerActivationComm());
        authorizeTransactionRentalInfo.setSalesActivationComm(commissionCategoryInfo.getSalesActivationComm());
        authorizeTransactionRentalInfo.setType(AuthorizeTransactionRentalInfo.TYPE_CONTRACTED);

        authorizeTransactionRentalInfo.setCardNumber(authRentUserInfo.getCardNumber());
        authorizeTransactionRentalInfo.setExpirationDate(authRentUserInfo.getExpirationDate());
        authorizeTransactionRentalInfo.setSecurityKey(authRentUserInfo.getSecurityKey());
        authorizeTransactionRentalInfo.setZipCode(authRentUserInfo.getZipCode());
        authorizeTransactionRentalInfo.setBillingAddress(authRentUserInfo.getBillingAddress());
        authorizeTransactionRentalInfo.setAmount(authorizeTransactionInfo.getAmount());
        authorizeTransactionRentalInfo.setTransactionId(authorizeTransactionInfo.getTransactionId());
        authorizeTransactionRentalInfo.setAuthCode(authorizeTransactionInfo.getAuthCode());
        authorizeTransactionRentalInfo.setStatus(authorizeTransactionInfo.getStatus());
        return authorizeTransactionRentalInfo;
    }

    public static AuthorizeTransactionRentalInfo monthlyFromAuthRentInfo(AuthRentUserInfo authRentUserInfo,
                                                                         AuthorizeTransactionInfo authorizeTransactionInfo){
        AuthorizeTransactionRentalInfo authorizeTransactionRentalInfo = new AuthorizeTransactionRentalInfo();

        authorizeTransactionRentalInfo.setClientKey(authRentUserInfo.getClientKey());
        authorizeTransactionRentalInfo.setSalesId(authRentUserInfo.getSalesId());
        authorizeTransactionRentalInfo.setDealerId(authRentUserInfo.getDealerId());
        authorizeTransactionRentalInfo.setCategory(authRentUserInfo.getCategory());
        authorizeTransactionRentalInfo.setDeposit(0);
        authorizeTransactionRentalInfo.setLdCommission(authRentUserInfo.getLdCommission());
        authorizeTransactionRentalInfo.setLdeCommission(authRentUserInfo.getLdeCommission());
        authorizeTransactionRentalInfo.setDealerCommission(authRentUserInfo.getDealerCommission());
        authorizeTransactionRentalInfo.setSalesCommission(authRentUserInfo.getSalesCommission());
        authorizeTransactionRentalInfo.setSvcCharge(authRentUserInfo.getSvcCharge());
        authorizeTransactionRentalInfo.setPrice(authRentUserInfo.getMonthPay());
        authorizeTransactionRentalInfo.setTxFee(Float.parseFloat(UnitUtil.round(authRentUserInfo.getMonthPay() * TAX)));
        authorizeTransactionRentalInfo.setType(AuthorizeTransactionRentalInfo.TYPE_MONTHLY);

        authorizeTransactionRentalInfo.setCardNumber(authRentUserInfo.getCardNumber());
        authorizeTransactionRentalInfo.setExpirationDate(authRentUserInfo.getExpirationDate());
        authorizeTransactionRentalInfo.setSecurityKey(authRentUserInfo.getSecurityKey());
        authorizeTransactionRentalInfo.setZipCode(authRentUserInfo.getZipCode());
        authorizeTransactionRentalInfo.setBillingAddress(authRentUserInfo.getBillingAddress());
        authorizeTransactionRentalInfo.setAmount(authorizeTransactionInfo.getAmount());
        authorizeTransactionRentalInfo.setTransactionId(authorizeTransactionInfo.getTransactionId());
        authorizeTransactionRentalInfo.setAuthCode(authorizeTransactionInfo.getAuthCode());
        authorizeTransactionRentalInfo.setStatus(authorizeTransactionInfo.getStatus());
        return authorizeTransactionRentalInfo;
    }
}
