package com.wiatec.panel.oxm.pojo;

import java.math.BigDecimal;

public class  CommissionCategoryInfo {

    private int id;
    private String category;
    private float deposit;
    private float monthPay;
    private int expires;
    private float ldCommission;
    private float dealerCommission;
    private float salesCommission;
    private float price;

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

    public float getMonthPay() {
        return monthPay;
    }

    public void setMonthPay(float monthPay) {
        this.monthPay = monthPay;
    }

    public int getExpires() {
        return expires;
    }

    public void setExpires(int expires) {
        this.expires = expires;
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

    public float getPrice() {
        return price;
    }

    public void setPrice() {
        BigDecimal b = new BigDecimal(this.monthPay * this.expires + this.deposit);
        this.price = b.setScale(2,  BigDecimal.ROUND_HALF_UP).floatValue();
    }

    @Override
    public String toString() {
        return "CommissionCategoryInfo{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", deposit=" + deposit +
                ", ldCommission=" + ldCommission +
                ", dealerCommission=" + dealerCommission +
                ", salesCommission=" + salesCommission +
                ", price=" + price +
                '}';
    }
}
