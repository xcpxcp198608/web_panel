package com.wiatec.panel.oxm.pojo.chart.admin;

public class TopAmountInfo {

    private int salesId;
    private String salesName;
    private float amount;


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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TopAmountInfo{" +
                "salesId=" + salesId +
                ", salesName='" + salesName + '\'' +
                ", amount=" + amount +
                '}';
    }
}
