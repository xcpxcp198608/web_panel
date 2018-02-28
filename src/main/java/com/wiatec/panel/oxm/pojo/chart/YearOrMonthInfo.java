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

    public YearOrMonthInfo() {
    }

    /**
     * 创建按年划分的实际段 （eg:2017-01-01   ~   2018-01-01）
     * @param year 开始年份
     * @param distributor distributor(LDE,LDM)
     */
    public YearOrMonthInfo(int year, int distributor) {
        this.start = year + "-01-01";
        this.end = (year + 1) + "-01-01";
        this.distributor = distributor;
    }

    /**
     * 创建按月划分的实际段(eg: 2017-01-01   ~  2018-02-01)
     * @param year start year
     * @param month start month
     * @param distributor distributor(LDE,LDM)
     */
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

    /**
     * 创建以当前月为基准向前倒退1年的时间段(eg: 2017-03-01   ~   2018-02-01)
     * @param year current year
     * @param month current month
     * @param distributor distributor(LDE,LDM)
     * @param is 与3个参数的构造区分
     */
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

    /**
     * 创建当前月的下个月的时间段（eg: 当前月是1月则时间段为 2017-02-01  ~ 2017-03-01）
     * @param year 当前年
     * @param month 当前月
     * @param distributor  distributor(LDE,LDM)
     * @return YearOrMonthInfo
     */
    public static YearOrMonthInfo createNextMonthly(int year, int month, int distributor){
        YearOrMonthInfo yearOrMonthInfo = new YearOrMonthInfo();
        yearOrMonthInfo.setDistributor(distributor);
        int startYear = year, endYear = 0, startMonth = 0, endMonth = 0;
        startMonth = month + 1;
        if(startMonth > 12){
            startMonth = 1;
            startYear ++;
        }
        String start = startYear + "-" + startMonth + "-01";
        endMonth = startMonth + 1;
        endYear = startYear;
        if(endMonth > 12){
            endMonth = 1;
            endYear ++;
        }
        String end = endYear + "-" + endMonth + "-01";
        yearOrMonthInfo.setStart(start);
        yearOrMonthInfo.setEnd(end);
        return yearOrMonthInfo;
    }

    public static void main (String [] args){
        YearOrMonthInfo yearOrMonthInfo = YearOrMonthInfo.createNextMonthly(2016, 11, 101);
        System.out.println(yearOrMonthInfo);

    }

}
