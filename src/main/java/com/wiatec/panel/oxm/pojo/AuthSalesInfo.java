package com.wiatec.panel.oxm.pojo;

import java.util.List;

public class AuthSalesInfo {

    private int id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private int ssn;
    private String email;
    private String phone;
    private String createTime;

    private List<AuthRentUserInfo> authRentUserInfoList;

    public AuthSalesInfo() {
    }

    public AuthSalesInfo(String username, String password) {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSsn() {
        return ssn;
    }

    public void setSsn(int ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<AuthRentUserInfo> getAuthRentUserInfoList() {
        return authRentUserInfoList;
    }

    public void setAuthRentUserInfoList(List<AuthRentUserInfo> authRentUserInfoList) {
        this.authRentUserInfoList = authRentUserInfoList;
    }

    @Override
    public String toString() {
        return "AuthSalesInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ssn=" + ssn +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime='" + createTime + '\'' +
                ", authRentUserInfoList=" + authRentUserInfoList +
                '}';
    }
}
