package com.wiatec.panel.oxm.pojo;


/**
 * @author patrick
 */
public class AuthEmployeeInfo {


    private int id;
    private String username;
    private String password;
    private int permission;

    public AuthEmployeeInfo() {
    }

    public AuthEmployeeInfo(String username) {
        this.username = username;
    }

    public AuthEmployeeInfo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    @Override
    public String toString() {
        return "AuthEmployeeInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                '}';
    }
}
