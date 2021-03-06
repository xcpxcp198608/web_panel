package com.wiatec.panel.oxm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wiatec.panel.common.utils.TimeUtil;

import java.util.Date;

/**
 * @author patrick
 */
public class DeviceAllInfo {

    public static final int STATUS_PENDING = 0;
    public static final int STATUS_MLM = 1;
    public static final int STATUS_PCP = 2;
    public static final int STATUS_CANCEL = -1;

    public static final String DISTRIBUTOR_LDE = "LDE";
    public static final String DISTRIBUTOR_LDM = "LDM";

    private int id;
    private String mac;
    private String distributor;
    private int status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date checkInTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifyTime;

    /**
     * 特别标志， 默认0， 等于1时表示是墨西哥的76台前11个月无需付费
     */
    private int flag;

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

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Date checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "DeviceAllInfo{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", distributor='" + distributor + '\'' +
                ", status=" + status +
                ", checkInTime=" + checkInTime +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                ", flag=" + flag +
                '}';
    }
}
