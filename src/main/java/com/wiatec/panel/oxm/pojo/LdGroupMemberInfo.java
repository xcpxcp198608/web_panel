package com.wiatec.panel.oxm.pojo;


import com.wiatec.panel.common.base.BaseInfo;

/**
 * @author patrick
 */
public class LdGroupMemberInfo extends BaseInfo {

    private int groupId;
    private int memberId;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    @Override
    public String toString() {
        return "LdGroupMemberInfo{" +
                "groupId=" + groupId +
                ", memberId=" + memberId +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
