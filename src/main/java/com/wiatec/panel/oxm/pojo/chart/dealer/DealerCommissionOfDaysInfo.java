package com.wiatec.panel.oxm.pojo.chart.dealer;

/**
 * @author patrick
 */
public class DealerCommissionOfDaysInfo {

    private float commission;
    private int day;

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "DealerCommissionOfDaysInfo{" +
                "commission=" + commission +
                ", day=" + day +
                '}';
    }
}
