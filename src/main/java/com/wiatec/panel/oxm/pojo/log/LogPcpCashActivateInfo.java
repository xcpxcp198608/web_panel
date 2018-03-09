package com.wiatec.panel.oxm.pojo.log;


import java.util.Date;

/**
 * @author patrick
 */
public class LogPcpCashActivateInfo {

    private int id;
    private String mac;
    private String executor;
    private Date createTime;

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

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "LogPcpCashActivateInfo{" +
                "id=" + id +
                ", mac='" + mac + '\'' +
                ", executor='" + executor + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
