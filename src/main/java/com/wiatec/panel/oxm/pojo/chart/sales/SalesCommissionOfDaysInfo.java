package com.wiatec.panel.oxm.pojo.chart.sales;

/**
 * @author patrick
 */
public class SalesCommissionOfDaysInfo {

    private int day;
    private float commission;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        commission = commission;
    }

    @Override
    public String toString() {
        return "SalesCommissionOfDaysInfo{" +
                "day=" + day +
                ", commission=" + commission +
                '}';
    }
}
