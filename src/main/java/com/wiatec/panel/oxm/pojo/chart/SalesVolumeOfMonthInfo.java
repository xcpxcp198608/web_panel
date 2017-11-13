package com.wiatec.panel.oxm.pojo.chart;

public class SalesVolumeOfMonthInfo {

    private int volume;
    private int b1;
    private int p1;
    private int p2;

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public int getB1() {
        return b1;
    }

    public void setB1(int b1) {
        this.b1 = b1;
    }

    public int getP1() {
        return p1;
    }

    public void setP1(int p1) {
        this.p1 = p1;
    }

    public int getP2() {
        return p2;
    }

    public void setP2(int p2) {
        this.p2 = p2;
    }

    @Override
    public String toString() {
        return "SalesVolumeOfMonthInfo{" +
                "volume=" + volume +
                ", b1=" + b1 +
                ", p1=" + p1 +
                ", p2=" + p2 +
                '}';
    }
}
