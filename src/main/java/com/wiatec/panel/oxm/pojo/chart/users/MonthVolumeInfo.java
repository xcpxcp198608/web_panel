package com.wiatec.panel.oxm.pojo.chart.users;

/**
 * @author patrick
 */
public class MonthVolumeInfo {

    private int day;
    private int volume;

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "MonthVolumeInfo{" +
                "day=" + day +
                ", volume=" + volume +
                '}';
    }
}
