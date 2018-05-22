package com.wiatec.panel.oxm.pojo.commission;

/**
 * @author patrick
 */
public class DealerMonthlyCommission {

    private int dealerId;
    private String dealerName;
    private float commission;
    private int volume;

    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "DealerMonthlyCommission{" +
                "dealerId=" + dealerId +
                ", dealerName='" + dealerName + '\'' +
                ", commission=" + commission +
                ", volume=" + volume +
                '}';
    }
}
