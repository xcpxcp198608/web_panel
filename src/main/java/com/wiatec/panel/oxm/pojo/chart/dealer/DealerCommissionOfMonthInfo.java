package com.wiatec.panel.oxm.pojo.chart.dealer;

/**
 * @author patrick
 */
public class DealerCommissionOfMonthInfo {

    private float commission;
    private int month;

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "DealerCommissionOfMonthInfo{" +
                "commission=" + commission +
                ", month=" + month +
                '}';
    }
}
