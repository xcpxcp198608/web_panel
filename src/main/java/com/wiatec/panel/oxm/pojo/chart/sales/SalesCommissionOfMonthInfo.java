package com.wiatec.panel.oxm.pojo.chart.sales;

/**
 * @author patrick
 */
public class SalesCommissionOfMonthInfo {

    private int month;
    private float commission;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        commission = commission;
    }

    @Override
    public String toString() {
        return "SalesCommissionOfMonthInfo{" +
                "month=" + month +
                ", commission=" + commission +
                '}';
    }
}
