package com.wiatec.panel.oxm.pojo;

import com.wiatec.panel.common.base.BaseInfo;

/**
 * @author patrick
 */
public class LdTrendingVoteInfo extends BaseInfo {

    private int trendingId;
    private int userId;

    public int getTrendingId() {
        return trendingId;
    }

    public void setTrendingId(int trendingId) {
        this.trendingId = trendingId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "LdTrendingVoteInfo{" +
                "trendingId=" + trendingId +
                ", userId=" + userId +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
