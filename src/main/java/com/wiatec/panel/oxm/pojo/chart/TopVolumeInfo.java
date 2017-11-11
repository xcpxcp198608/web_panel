package com.wiatec.panel.oxm.pojo.chart;

import com.wiatec.panel.oxm.pojo.AuthSalesInfo;

public class TopVolumeInfo {

    private int salesId;
    private String salesName;
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

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "TopVolumeInfo{" +
                "salesId=" + salesId +
                ", salesName='" + salesName + '\'' +
                ", volume=" + volume +
                '}';
    }
}
