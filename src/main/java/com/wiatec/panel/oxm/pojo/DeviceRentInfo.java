package com.wiatec.panel.oxm.pojo;


import com.wiatec.panel.common.utils.TimeUtil;

import java.util.Date;

/**
 * @author patrick
 */
public class DeviceRentInfo {

    private int id;
    private String mac;
    private int salesId;
    private String salesName;
    private int dealerId;
    private String dealerName;
    private int adminId;
    private Date createTime;

    private boolean rented;
    private Date rentTime;
    /**
     * security deposit credite note
     */
    private boolean sdcn;
    /**
     * the deposit is returned?
     */
    private boolean checked;
    private String checkNumber;
    private Date checkTime;

    public DeviceRentInfo() {
    }

    public DeviceRentInfo(String mac) {
        this.mac = mac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public boolean isSdcn() {
        return sdcn;
    }

    public void setSdcn(boolean sdcn) {
        this.sdcn = sdcn;
    }

    public String getCreateTime() {
        if(createTime == null){
            return "";
        }else {
            return TimeUtil.FORMATTER.format(createTime);
        }
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRentTime() {
        if(rentTime == null){
            return "";
        }else {
            return TimeUtil.FORMATTER.format(rentTime);
        }
    }

    public void setRentTime(Date rentTime) {
        this.rentTime = rentTime;
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

    public String getCheckTime() {
        if(checkTime == null){
            return "";
        }else {
            return TimeUtil.FORMATTER.format(checkTime);
        }
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    @Override
    public String toString() {
        return "DeviceRentInfo{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", salesId=" + salesId +
                ", salesName='" + salesName + '\'' +
                ", dealerId=" + dealerId +
                ", dealerName='" + dealerName + '\'' +
                ", adminId=" + adminId +
                ", createTime=" + createTime +
                ", rented=" + rented +
                ", rentTime=" + rentTime +
                ", sdcn=" + sdcn +
                ", checked=" + checked +
                ", checkNumber='" + checkNumber + '\'' +
                ", checkTime=" + checkTime +
                '}';
    }
}
