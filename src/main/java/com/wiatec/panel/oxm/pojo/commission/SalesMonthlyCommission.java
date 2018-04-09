package com.wiatec.panel.oxm.pojo.commission;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author patrick
 */
public class SalesMonthlyCommission {

    private int salesId;
    private String salesName;
    private float commission;
    private int volume;

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
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
        return "SalesMonthlyCommission{" +
                "salesId=" + salesId +
                ", salesName='" + salesName + '\'' +
                ", commission=" + commission +
                ", volume=" + volume +
                '}';
    }
}
