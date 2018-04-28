package com.wiatec.panel.oxm.pojo;


import com.wiatec.panel.common.base.BaseInfo;

/**
 * @author patrick
 */

public class LdFriendInfo  extends BaseInfo {

    private int userId;
    private int friendId;
    private boolean approved;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFriendId() {
        return friendId;
    }

    public void setFriendId(int friendId) {
        this.friendId = friendId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String toString() {
        return "LdFriendInfo{" +
                "userId=" + userId +
                ", friendId=" + friendId +
                ", approved=" + approved +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
