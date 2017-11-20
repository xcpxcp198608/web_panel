package com.wiatec.panel.oxm.pojo.chart.admin;

public class SalesAmountInfo {

    private float price;
    private float txFee;
    private float deposit;
    private float ldCommission;
    private float dealerCommission;
    private float salesCommission;
    private int date;

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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SalesAmountInfo{" +
                "price=" + price +
                ", txFee=" + txFee +
                ", deposit=" + deposit +
                ", ldCommission=" + ldCommission +
                ", dealerCommission=" + dealerCommission +
                ", salesCommission=" + salesCommission +
                ", date=" + date +
                '}';
    }
}
