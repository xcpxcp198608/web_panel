package com.wiatec.panel.oxm.pojo;

import com.wiatec.panel.common.utils.TimeUtil;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author patrick
 */
public class AuthSalesInfo {

    /**
     * 当gold销售卖出指定台数机器时才可以发放佣金
     */
    public static final int SDCN_NOTICE_COUNT = 5;
    /**
     * 当销售屯货达到此数量时自动成为gold
     */
    public static final int GOLD_COUNT = 10;


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
            message = "email input format incorrect")
    private String email;

    @Size(min = 10, max = 12, message = "phone number input format incorrect")
    private String phone;
    /**
     * 发放销售佣金的银行账号
     */
    private String bankInfo;

    /**
     * 销售激活账号的收费类型 AM1 AM2
     */
    private String activateCategory;

    private String cardNumber;
    private String expirationDate;
    private String securityKey;
    private String zipCode;
    private String billingAddress;

    /**
     * 当销售一次拿货10台或以上时此属性为true，salesCommission + 1;
     */
    private boolean isGold;
    /**
     * 当销售5台以后，可以进行退已销售台数的押金或继续补货已销售台数
     * security deposit credit note
     */
    private boolean sdcn;
    /**
     * 销售账号有效日期
     */
    private Date expiresTime;
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

    public String getActivateCategory() {
        return activateCategory;
    }

    public void setActivateCategory(String activateCategory) {
        this.activateCategory = activateCategory;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public boolean isGold() {
        return isGold;
    }

    public void setGold(boolean gold) {
        isGold = gold;
    }

    public boolean isSdcn() {
        return sdcn;
    }

    public void setSdcn(boolean sdcn) {
        this.sdcn = sdcn;
    }

    public String getExpiresTime() {
        if(expiresTime == null){
            return "";
        }else {
            return TimeUtil.FORMATTER.format(expiresTime);
        }
    }

    public void setExpiresTime(Date expiresTime) {
        this.expiresTime = expiresTime;
    }

    public String getCreateTime() {
        if(createTime == null){
            return "";
        }else {
            return TimeUtil.FORMATTER.format(createTime);
        }
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
                ", activateCategory='" + activateCategory + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", securityKey='" + securityKey + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", isGold=" + isGold +
                ", sdcn=" + sdcn +
                ", expiresTime=" + expiresTime +
                ", createTime=" + createTime +
                '}';
    }
}
