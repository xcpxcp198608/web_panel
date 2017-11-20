package com.wiatec.panel.oxm.pojo.chart.admin;

public class SalesDayVolumeInMonthInfo {

    private String category;
    private int count;
    private int day;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "SalesDayVolumeInMonthInfo{" +
                "category='" + category + '\'' +
                ", count=" + count +
                ", day=" + day +
                '}';
    }
}
