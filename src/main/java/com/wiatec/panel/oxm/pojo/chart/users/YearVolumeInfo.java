package com.wiatec.panel.oxm.pojo.chart.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author patrick
 */
public class YearVolumeInfo {

    private int month;
    private int volume;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "YearVolumeInfo{" +
                "month=" + month +
                ", volume=" + volume +
                '}';
    }
}
