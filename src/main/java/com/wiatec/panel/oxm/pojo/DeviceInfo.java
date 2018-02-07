package com.wiatec.panel.oxm.pojo;

import com.wiatec.panel.common.utils.TimeUtil;

import java.util.Date;

/**
 * @author patrick
 */
public class DeviceInfo {

    private int id;
    private String mac;
    private boolean enable;
    private String enableName;
    private Date enableTime;
    private Date createTime;
    private Date modifyTime;

    public DeviceInfo() {
    }

    public DeviceInfo(String mac) {
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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getEnableName() {
        return enableName;
    }

    public void setEnableName(String enableName) {
        this.enableName = enableName;
    }

    public String getEnableTime() {
        return TimeUtil.FORMATTER.format(enableTime);
    }

    public void setEnableTime(Date enableTime) {
        this.enableTime = enableTime;
    }

    public String getCreateTime() {
        return TimeUtil.FORMATTER.format(createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return TimeUtil.FORMATTER.format(modifyTime);
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "DeviceInfo{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", enable=" + enable +
                ", enableName='" + enableName + '\'' +
                ", enableTime=" + enableTime +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
