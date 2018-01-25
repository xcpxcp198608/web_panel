package com.wiatec.panel.oxm.pojo;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author patrick
 */
public class AuthSalesInfo {

    private static final SimpleDateFormat FORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private int id;
    @Size(min = 6, max = 20, message = "username input format incorrect")
    private String username;
    @Size(min = 6, max = 20, message = "password input format incorrect")
    private String password;
    private int dealerId;
    private String dealerName;
    private String firstName;
    private String lastName;
    @Size(min = 9, max = 9, message = "ssn input format incorrect")
    private String ssn;
    @Pattern(regexp = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$",
            message = "email input format incorrent")
    private String email;
    private String bankInfo;
    @Size(min = 10, max = 12, message = "phone number input format incorrect")
    private String phone;

    /**
     * 当销售一次拿货10台或以上时此属性为true，salesCommission + 1;
     */
    private boolean isGold;
    private Date createTime;

    public AuthSalesInfo() {
    }

    public AuthSalesInfo(String username) {
        this.username = username;
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

    public int getDealerId() {
        return dealerId;
    }

    public void setDealerId(int dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
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

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankInfo() {
        return bankInfo;
    }

    public void setBankInfo(String bankInfo) {
        this.bankInfo = bankInfo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isGold() {
        return isGold;
    }

    public void setGold(boolean gold) {
        isGold = gold;
    }

    public String getCreateTime() {
        return FORMATTER.format(createTime);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "AuthSalesInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dealerId=" + dealerId +
                ", dealerName='" + dealerName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", ssn='" + ssn + '\'' +
                ", email='" + email + '\'' +
                ", bankInfo='" + bankInfo + '\'' +
                ", phone='" + phone + '\'' +
                ", isGold=" + isGold +
                ", createTime=" + createTime +
                '}';
    }
}
