package com.wiatec.panel.oxm.pojo.chart;

public class SalesMonthCommissionInfo {

    private int month;
    private float Commission;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public float getCommission() {
        return Commission;
    }

    public void setCommission(float commission) {
        Commission = commission;
    }

    @Override
    public String toString() {
        return "SalesMonthCommissionInfo{" +
                "month=" + month +
                ", Commission=" + Commission +
                '}';
    }
}
