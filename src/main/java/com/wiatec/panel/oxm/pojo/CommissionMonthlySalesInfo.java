package com.wiatec.panel.oxm.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * @author patrick
 */
public class CommissionMonthlySales {

    private int id;
    private int salesId;
    private String mac;
    private float commission;
    private boolean checked;
    private String checkNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getCheckNumber() {
        return checkNumber;
    }

    public void setCheckNumber(String checkNumber) {
        this.checkNumber = checkNumber;
    }

    public Date getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
    }

    @Override
    public String toString() {
        return "CommissionMonthlySales{" +
                "id=" + id +
                ", salesId=" + salesId +
                ", mac='" + mac + '\'' +
                ", commission=" + commission +
                ", checked=" + checked +
                ", checkNumber='" + checkNumber + '\'' +
                ", crateTime=" + crateTime +
                '}';
    }
}
