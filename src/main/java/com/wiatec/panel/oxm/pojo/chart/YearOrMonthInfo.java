package com.wiatec.panel.oxm.pojo.chart;

/**
 * @author patrick
 */
public class YearOrMonthInfo {

    private String start;
    private String end;
    private String salesId;
    private String dealerId;

    public YearOrMonthInfo(int year) {
        this.start = year + "-01-01";
        this.end = (year + 1) + "-01-01";
    }

    public YearOrMonthInfo(int year, int month) {
        this.start = year + "-" + month + "-01";
        if(month == 12){
            this.end = (year + 1) + "-01-01";
        }else{
            int e = month + 1;
            if(e < 10){
                this.end = year + "-0" + e + "-01";
            }else {
                this.end = year + "-" + e + "-01";
            }
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

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    @Override
    public String toString() {
        return "YearOrMonthInfo{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", salesId='" + salesId + '\'' +
                ", dealerId='" + dealerId + '\'' +
                '}';
    }
}
