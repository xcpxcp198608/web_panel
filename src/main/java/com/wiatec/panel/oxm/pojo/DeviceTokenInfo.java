package com.wiatec.panel.oxm.pojo;



/**
 * @author patrick
 */
public class DeviceTokenInfo {

    private int id;
    private int userId;
    private String deviceToken;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    @Override
    public String toString() {
        return "DeviceTokenInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", deviceToken='" + deviceToken + '\'' +
                '}';
    }
}
