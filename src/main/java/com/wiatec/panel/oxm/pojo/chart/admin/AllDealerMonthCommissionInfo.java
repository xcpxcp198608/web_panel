package com.wiatec.panel.oxm.pojo.chart.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author patrick
 */
public class AllDealerMonthCommissionInfo {

    private String dealerUsername;
    private int volume;
    private float commission;

    public String getDealerUsername() {
        return dealerUsername;
    }

    public void setDealerUsername(String dealerUsername) {
        this.dealerUsername = dealerUsername;
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
        return "AllDealerMonthCommissionInfo{" +
                "dealerUsername='" + dealerUsername + '\'' +
                ", volume=" + volume +
                ", commission=" + commission +
                '}';
    }
}
