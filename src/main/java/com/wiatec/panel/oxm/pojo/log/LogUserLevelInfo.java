package com.wiatec.panel.oxm.pojo.log;

import com.wiatec.panel.oxm.pojo.AuthRegisterUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author patrick
 */
public class LogUserLevelInfo {

    private int id;
    private int userId;
    private String username;
    private int level;
    private Date expiration;
    private int executorId;
    private String executorName;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public int getExecutorId() {
        return executorId;
    }

    public void setExecutorId(int executorId) {
        this.executorId = executorId;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "LogUserLevelInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", level=" + level +
                ", expiration=" + expiration +
                ", executorId=" + executorId +
                ", executorName='" + executorName + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public static LogUserLevelInfo createFromRegisterUser(AuthRegisterUserInfo authRegisterUserInfo){
        LogUserLevelInfo logUserLevelInfo = new LogUserLevelInfo();
        logUserLevelInfo.setUserId(authRegisterUserInfo.getId());
        logUserLevelInfo.setUsername(authRegisterUserInfo.getUsername());
        logUserLevelInfo.setLevel(authRegisterUserInfo.getLevel());
        logUserLevelInfo.setExpiration(authRegisterUserInfo.getExpiration());
        return logUserLevelInfo;
    }

}
