package com.wiatec.panel.oxm.pojo.chart.users;

public class LevelInfo {

    private int count;
    private int month;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "LevelInfo{" +
                "count=" + count +
                ", month=" + month +
                '}';
    }
}
