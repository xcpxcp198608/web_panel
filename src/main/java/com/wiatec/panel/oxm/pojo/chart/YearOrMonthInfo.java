package com.wiatec.panel.oxm.pojo.chart;

/**
 * @author patrick
 */
public class YearOrMonthInfo {

    private String start;
    private String end;
    private String salesId;
    private String dealerId;
    private int distributor;

    public YearOrMonthInfo(int year, int distributor) {
        this.start = year + "-01-01";
        this.end = (year + 1) + "-01-01";
        this.distributor = distributor;
    }

    public YearOrMonthInfo(int year, int month, int distributor) {
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
        this.distributor = distributor;
    }

    public YearOrMonthInfo(int year, int month, int distributor, boolean is) {
        month += 1;
        if(month > 12){
            month = 1;
            this.end = (year+1) + "-0" + month + "-01";
        }else {
            if (month < 10) {
                this.end = year + "-0" + month + "-01";
            } else {
                this.end = year + "-" + month + "-01";
            }
        }
        if(month >= 12){
            this.start = year + "-01-01";
        }else{
            int startMonth = month - 12 + 13;
            if(startMonth < 10){
                this.start = (year-1) + "-0" + startMonth + "-01";
            }else {
                this.start = (year-1) + "-" + startMonth + "-01";
            }
        }
        this.distributor = distributor;
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

    public int getDistributor() {
        return distributor;
    }

    public void setDistributor(int distributor) {
        this.distributor = distributor;
    }

    @Override
    public String toString() {
        return "YearOrMonthInfo{" +
                "start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", salesId='" + salesId + '\'' +
                ", dealerId='" + dealerId + '\'' +
                ", distributor=" + distributor +
                '}';
    }
}
