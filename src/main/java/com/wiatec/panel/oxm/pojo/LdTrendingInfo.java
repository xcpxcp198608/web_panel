package com.wiatec.panel.oxm.pojo;

import com.wiatec.panel.common.base.BaseInfo;

/**
 * @author patrick
 */
public class LdTrendingInfo extends BaseInfo {

    private int userId;
    private String username;
    private String content;
    private int imgCount;
    private String imgUrl;
    private String link;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getImgCount() {
        return imgCount;
    }

    public void setImgCount(int imgCount) {
        this.imgCount = imgCount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "LdTrendingInfo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", content='" + content + '\'' +
                ", imgCount=" + imgCount +
                ", imgUrl='" + imgUrl + '\'' +
                ", link='" + link + '\'' +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
