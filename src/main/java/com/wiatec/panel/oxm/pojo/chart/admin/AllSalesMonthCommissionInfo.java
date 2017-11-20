package com.wiatec.panel.oxm.pojo.chart.admin;

public class AllSalesMonthCommissionInfo {

    private String salesUsername;
    private float commission;

    public String getSalesUsername() {
        return salesUsername;
    }

    public void setSalesUsername(String salesUsername) {
        this.salesUsername = salesUsername;
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
                ", commission=" + commission +
                '}';
    }
}
