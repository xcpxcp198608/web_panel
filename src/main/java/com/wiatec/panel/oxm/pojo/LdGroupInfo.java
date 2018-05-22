package com.wiatec.panel.oxm.pojo;


import com.wiatec.panel.common.base.BaseInfo;

/**
 * @author patrick
 */
public class LdGroupInfo extends BaseInfo {

    private int groupId;
    /**
     *
     * 所有者id
     */
    private int ownerId;
    /**
     * 人数
     */
    private int numbers;
    private String name;
    private String description;
    private String icon;


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getNumbers() {
        return numbers;
    }

    public void setNumbers(int numbers) {
        this.numbers = numbers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "LdGroupInfo{" +
                "groupId=" + groupId +
                ", ownerId=" + ownerId +
                ", numbers=" + numbers +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
