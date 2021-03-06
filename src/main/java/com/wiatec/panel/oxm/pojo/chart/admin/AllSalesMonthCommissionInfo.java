package com.wiatec.panel.oxm.pojo.chart.admin;

/**
 * @author patrick
 */
public class AllSalesMonthCommissionInfo {

    private String salesUsername;
    private int volume;
    private float commission;

    public String getSalesUsername() {
        return salesUsername;
    }

    public void setSalesUsername(String salesUsername) {
        this.salesUsername = salesUsername;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    @Override
    public String toString() {
        return "AllSalesMonthCommissionInfo{" +
                "salesUsername='" + salesUsername + '\'' +
                ", volume=" + volume +
                ", commission=" + commission +
                '}';
    }
}
