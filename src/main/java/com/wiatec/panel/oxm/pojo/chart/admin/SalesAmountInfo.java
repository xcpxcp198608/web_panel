package com.wiatec.panel.oxm.pojo.chart.admin;

/**
 * @author patrick
 */
public class SalesAmountInfo {

    private float amount;
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

    private int date;

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

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "SalesAmountInfo{" +
                "amount=" + amount +
                ", price=" + price +
                ", txFee=" + txFee +
                ", deposit=" + deposit +
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
                ", date=" + date +
                '}';
    }
}
