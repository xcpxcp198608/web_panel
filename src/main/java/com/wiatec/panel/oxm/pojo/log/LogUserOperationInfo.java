package com.wiatec.panel.oxm.pojo.log;

import com.wiatec.panel.common.base.BaseInfo;

/**
 * @author patrick
 */
public class LogUserOperationInfo extends BaseInfo {

    private int userId;
    private int action;
    private String description;
    private String platform;
    private String remark;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "LogUserOperationInfo{" +
                "userId=" + userId +
                ", action=" + action +
                ", description='" + description + '\'' +
                ", platform='" + platform + '\'' +
                ", remark='" + remark + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
