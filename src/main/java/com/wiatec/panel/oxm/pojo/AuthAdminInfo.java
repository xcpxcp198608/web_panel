package com.wiatec.panel.oxm.pojo;

import javax.validation.constraints.Size;

/**
 * @author patrick
 */
public class AuthAdminInfo {

    public static final int LEVEL_HIGH = 101;
    public static final int LEVEL_NORMAL = 0;


    private int id;
    @Size(min = 6, max = 20, message = "username input format incorrect")
    private String username;
    @Size(min = 6, max = 20, message = "password input format incorrect")
    private String password;
    private int permission;

    public AuthAdminInfo() {
    }

    public AuthAdminInfo(String username) {
        this.username = username;
    }

    public AuthAdminInfo(String username, String password) {
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
        return "AuthAdminInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", permission=" + permission +
                '}';
    }
}
