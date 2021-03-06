package com.wiatec.panel.oxm.pojo;

import com.wiatec.panel.common.base.BaseInfo;

/**
 * @author patrick
 */
public class LdTrendingInfo extends BaseInfo {

    private int userId;
    private String username;
    private String icon;
    private String content;
    private int imgCount;
    private String imgUrl;
    private String link;
    private int voteCount;
    private boolean voted;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
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

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }

    @Override
    public String toString() {
        return "LdTrendingInfo{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", icon='" + icon + '\'' +
                ", content='" + content + '\'' +
                ", imgCount=" + imgCount +
                ", imgUrl='" + imgUrl + '\'' +
                ", link='" + link + '\'' +
                ", voteCount=" + voteCount +
                ", voted=" + voted +
                ", id=" + id +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                '}';
    }
}
