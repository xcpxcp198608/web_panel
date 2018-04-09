package com.wiatec.panel.authorize;

import com.wiatec.panel.oxm.pojo.AuthSalesInfo;
import com.wiatec.panel.oxm.pojo.SalesActivateCategoryInfo;

/**
 * @author patrick
 */
public class AuthorizeTransactionSalesMemberInfo extends AuthorizeTransactionInfo {

    private int salesId;
    private String salesName;
    private String activateCategory;
    private float activatePay;

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

    @Override
    public String toString() {
        return "AuthorizeTransactionSalesMemberInfo{" +
                "salesId=" + salesId +
                ", salesName='" + salesName + '\'' +
                ", activateCategory='" + activateCategory + '\'' +
                ", activatePay=" + activatePay +
                '}';
    }

    public static AuthorizeTransactionSalesMemberInfo create(AuthSalesInfo authSalesInfo,
                                                             SalesActivateCategoryInfo salesActivateCategoryInfo,
                                                             AuthorizeTransactionInfo authorizeTransactionInfo){
        AuthorizeTransactionSalesMemberInfo authorizeTransactionSalesMemberInfo = new AuthorizeTransactionSalesMemberInfo();
        authorizeTransactionSalesMemberInfo.setSalesId(authSalesInfo.getId());
        authorizeTransactionSalesMemberInfo.setActivateCategory(salesActivateCategoryInfo.getCategory());
        authorizeTransactionSalesMemberInfo.setActivatePay(salesActivateCategoryInfo.getPrice());

        authorizeTransactionSalesMemberInfo.setCardNumber(authorizeTransactionInfo.getCardNumber());
        authorizeTransactionSalesMemberInfo.setExpirationDate(authorizeTransactionInfo.getExpirationDate());
        authorizeTransactionSalesMemberInfo.setSecurityKey(authorizeTransactionInfo.getSecurityKey());
        authorizeTransactionSalesMemberInfo.setZipCode(authorizeTransactionInfo.getZipCode());
        authorizeTransactionSalesMemberInfo.setBillingAddress(authorizeTransactionInfo.getBillingAddress());
        authorizeTransactionSalesMemberInfo.setAmount(authorizeTransactionInfo.getAmount());
        authorizeTransactionSalesMemberInfo.setTransactionId(authorizeTransactionInfo.getTransactionId());
        authorizeTransactionSalesMemberInfo.setAuthCode(authorizeTransactionInfo.getAuthCode());
        authorizeTransactionSalesMemberInfo.setStatus(authorizeTransactionInfo.getStatus());
        return authorizeTransactionSalesMemberInfo;
    }
}
