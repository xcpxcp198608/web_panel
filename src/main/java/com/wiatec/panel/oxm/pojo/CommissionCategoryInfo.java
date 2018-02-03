package com.wiatec.panel.oxm.pojo;

import java.math.BigDecimal;

/**
 * @author patrick
 */
public class  CommissionCategoryInfo {

    public static final String CATEGORY_B1 = "B1";
    public static final String CATEGORY_P1 = "P1";
    public static final String CATEGORY_P2 = "P2";
    public static final float DEPOSIT = 100F;

    private int id;
    private String category;
    private float deposit;
    private int expires;
    private int bonus;
    private float ldCommission;
    private float ldeCommission;
    private float dealerCommission;
    private float salesCommission;

    /**
     * Credit Card banking service charge
     */
    private float svcCharge;
    private float activatePay;
    private float ldActivationComm;
    private float ldeActivationComm;
    private float dealerActivationComm;
    private float salesActivationComm;

    private float price;
    private float firstPay;
    private float monthPay;
    private String description;

    public float getPrice() {
        return price;
    }

    public void setPrice() {
        BigDecimal b = new BigDecimal(this.monthPay * this.expires + this.deposit + this.activatePay);
        this.price = b.setScale(2,  BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public float getFirstPay() {
        return firstPay;
    }

    public void setFirstPay() {
        BigDecimal b = new BigDecimal(this.monthPay + this.deposit + this.activatePay);
        this.firstPay = b.setScale(2,  BigDecimal.ROUND_HALF_UP).floatValue();
    }

    public float getMonthPay() {
        return monthPay;
    }

    public void setMonthPay(float monthPay) {
        this.monthPay = monthPay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
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

    public float getActivatePay() {
        return activatePay;
    }

    public void setActivatePay(float activatePay) {
        this.activatePay = activatePay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getSvcCharge() {
        return svcCharge;
    }

    public void setSvcCharge(float svcCharge) {
        this.svcCharge = svcCharge;
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
        return "CommissionCategoryInfo{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", deposit=" + deposit +
                ", expires=" + expires +
                ", bonus=" + bonus +
                ", ldCommission=" + ldCommission +
                ", ldeCommission=" + ldeCommission +
                ", dealerCommission=" + dealerCommission +
                ", salesCommission=" + salesCommission +
                ", svcCharge=" + svcCharge +
                ", activatePay=" + activatePay +
                ", ldActivationComm=" + ldActivationComm +
                ", ldeActivationComm=" + ldeActivationComm +
                ", dealerActivationComm=" + dealerActivationComm +
                ", salesActivationComm=" + salesActivationComm +
                ", price=" + price +
                ", firstPay=" + firstPay +
                ", monthPay=" + monthPay +
                ", description='" + description + '\'' +
                '}';
    }
}
