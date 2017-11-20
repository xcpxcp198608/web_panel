package com.wiatec.panel.oxm.pojo.chart.sales;

public class SalesCommissionOfDaysInfo {

    private int day;
    private float Commission;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public float getCommission() {
        return Commission;
    }

    public void setCommission(float commission) {
        Commission = commission;
    }

    @Override
    public String toString() {
        return "SalesCommissionOfDaysInfo{" +
                "day=" + day +
                ", Commission=" + Commission +
                '}';
    }
}
