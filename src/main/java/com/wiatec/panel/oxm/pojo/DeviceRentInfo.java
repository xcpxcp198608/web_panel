package com.wiatec.panel.oxm.pojo;


/**
 * @author patrick
 */
public class DeviceRentInfo {

    private int id;
    private String mac;
    private int dealerId;
    private String dealerName;
    private int adminId;
    private String createTime;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "DeviceRentInfo{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", dealerId=" + dealerId +
                ", dealerName='" + dealerName + '\'' +
                ", adminId=" + adminId +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
