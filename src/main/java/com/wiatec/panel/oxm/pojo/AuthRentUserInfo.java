package com.wiatec.panel.oxm.pojo;

public class AuthRentUserInfo {

    public static final String STATUS_ACTIVATE = "activate";
    public static final String STATUS_DEACTIVATE = "deactivate";
    public static final String STATUS_LIMITED = "limited";
    public static final String STATUS_CANCELED = "canceled";

    private int id;
    private int salesId;
    private String salesName;
    private int dealerId;
    private String dealerName;
    private String clientKey;
    private String category;
    private String mac;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String cardNumber;
    private int expirationDate;
    private int securityKey;
    private float deposit;
    private float firstPay;
    private float monthPay;
    private float ldCommission;
    private float dealerCommission;
    private float salesCommission;
    private String createTime;
    private String activateTime;
    private String expiresTime;
    private String status;
    private String country;
    private String region;
    private String city;
    private String timeZone;
    private String lastOnLineTime;

    private boolean online;

    public AuthRentUserInfo() {
    }

    public AuthRentUserInfo(String clientKey, String mac) {
        this.clientKey = clientKey;
        this.mac = mac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
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

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
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

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(int expirationDate) {
        this.expirationDate = expirationDate;
    }

    public int getSecurityKey() {
        return securityKey;
    }

    public void setSecurityKey(int securityKey) {
        this.securityKey = securityKey;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public float getFirstPay() {
        return firstPay;
    }

    public void setFirstPay(float firstPay) {
        this.firstPay = firstPay;
    }

    public float getMonthPay() {
        return monthPay;
    }

    public void setMonthPay(float monthPay) {
        this.monthPay = monthPay;
    }

    public float getLdCommission() {
        return ldCommission;
    }

    public void setLdCommission(float ldCommission) {
        this.ldCommission = ldCommission;
    }

    public float getDealerCommission() {
        return dealerCommission;
    }

    public void setDealerCommission(float dealerCommission) {
        this.dealerCommission = dealerCommission;
    }

    public float getSalesCommission() {
        return salesCommission;
    }

    public void setSalesCommission(float salesCommission) {
        this.salesCommission = salesCommission;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getActivateTime() {
        return activateTime;
    }

    public void setActivateTime(String activateTime) {
        this.activateTime = activateTime;
    }

    public String getExpiresTime() {
        return expiresTime;
    }

    public void setExpiresTime(String expiresTime) {
        this.expiresTime = expiresTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getLastOnLineTime() {
        return lastOnLineTime;
    }

    public void setLastOnLineTime(String lastOnLineTime) {
        this.lastOnLineTime = lastOnLineTime;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public String toString() {
        return "AuthRentUserInfo{" +
                "id=" + id +
                ", salesId=" + salesId +
                ", salesName='" + salesName + '\'' +
                ", dealerId=" + dealerId +
                ", dealerName='" + dealerName + '\'' +
                ", clientKey='" + clientKey + '\'' +
                ", category='" + category + '\'' +
                ", mac='" + mac + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate=" + expirationDate +
                ", securityKey=" + securityKey +
                ", deposit=" + deposit +
                ", firstPay=" + firstPay +
                ", monthPay=" + monthPay +
                ", ldCommission=" + ldCommission +
                ", dealerCommission=" + dealerCommission +
                ", salesCommission=" + salesCommission +
                ", createTime='" + createTime + '\'' +
                ", activateTime='" + activateTime + '\'' +
                ", expiresTime='" + expiresTime + '\'' +
                ", status='" + status + '\'' +
                ", country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", timeZone='" + timeZone + '\'' +
                ", lastOnLineTime='" + lastOnLineTime + '\'' +
                ", online=" + online +
                '}';
    }
}
