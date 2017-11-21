package com.wiatec.panel.oxm.pojo.chart;

public class YearOrMonthInfo {

    private String start;
    private String end;
    private String salesId;

    public YearOrMonthInfo(int year) {
        this.start = year + "-1-1";
        this.end = (year+1) + "-1-1";
    }

    public YearOrMonthInfo(int year, int month) {
        this.start = year + "-" + month + "-1";
        if(month == 12){
            this.end = (year + 1) + "-1-1";
        }else{
            this.end = year + "-" + (month + 1) + "-1";
        }
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getSalesId() {
        return salesId;
    }

    public void setSalesId(String salesId) {
        this.salesId = salesId;
    }

    @Override
    public String toString() {
        return "YearOrMonthInfo{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", salesId='" + salesId + '\'' +
                '}';
    }
}
