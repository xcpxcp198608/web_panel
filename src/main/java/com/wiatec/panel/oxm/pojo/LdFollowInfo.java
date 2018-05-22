package com.wiatec.panel.oxm.pojo;


import com.wiatec.panel.common.base.BaseInfo;

/**
 * @author patrick
 */
public class LdFollowInfo extends BaseInfo {

    private int followerId;
    private int followId;

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public int getFollowId() {
        return followId;
    }

    public void setFollowId(int followId) {
        this.followId = followId;
    }

    @Override
    public String toString() {
        return "LdFollowInfo{" +
                "followerId=" + followerId +
                ", followId=" + followId +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
