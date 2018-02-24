package com.wiatec.panel.oxm.pojo;

/**
 * @author patrick
 */
public class AuthRentUserInfo {

    public static final String STATUS_ACTIVATE = "activate";
    public static final String STATUS_DEACTIVATE = "deactivate";
    public static final String STATUS_LIMITED = "limited";
    public static final String STATUS_CANCELED = "canceled";

    public static final String PAYMENT_CASH = "cash";
    public static final String PAYMENT_CREDIT_CARD = "credit_card";
    public static final String PAYMENT_PAYPAL = "paypal";

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
    private String postCode;
    private String postAddress;
    private boolean express;
    private String cardNumber;
    private String expirationDate;
    private String securityKey;
    private String zipCode;
    private String billingAddress;

    private String paymentType;
    private float deposit;
    private float firstPay;
    private float monthPay;
    private float svcCharge;
    private float ldCommission;
    private float ldeCommission;
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
    private String deviceModel;
    private String romVersion;
    private String uiVersion;
    private String lastOnLineTime;

    private boolean online;

    public AuthRentUserInfo() {
    }

    public AuthRentUserInfo(String mac) {
        this.mac = mac;
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

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostAddress() {
        return postAddress;
    }

    public void setPostAddress(String postAddress) {
        this.postAddress = postAddress;
    }

    public boolean isExpress() {
        return express;
    }

    public void setExpress(boolean express) {
        this.express = express;
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public float getSvcCharge() {
        return svcCharge;
    }

    public void setSvcCharge(float svcCharge) {
        this.svcCharge = svcCharge;
    }

    public float getLdCommission() {
        return ldCommission;
    }

    public void setLdCommission(float ldCommission) {
        this.ldCommission = ldCommission;
    }

    public float getLdeCommission() {
        return ldeCommission;
    }

    public void setLdeCommission(float ldeCommission) {
        this.ldeCommission = ldeCommission;
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

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getRomVersion() {
        return romVersion;
    }

    public void setRomVersion(String romVersion) {
        this.romVersion = romVersion;
    }

    public String getUiVersion() {
        return uiVersion;
    }

    public void setUiVersion(String uiVersion) {
        this.uiVersion = uiVersion;
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
                ", postCode='" + postCode + '\'' +
                ", postAddress='" + postAddress + '\'' +
                ", express=" + express +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate='" + expirationDate + '\'' +
                ", securityKey='" + securityKey + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", billingAddress='" + billingAddress + '\'' +
                ", paymentType='" + paymentType + '\'' +
                ", deposit=" + deposit +
                ", firstPay=" + firstPay +
                ", monthPay=" + monthPay +
                ", svcCharge=" + svcCharge +
                ", ldCommission=" + ldCommission +
                ", ldeCommission=" + ldeCommission +
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
                ", deviceModel='" + deviceModel + '\'' +
                ", romVersion='" + romVersion + '\'' +
                ", uiVersion='" + uiVersion + '\'' +
                ", lastOnLineTime='" + lastOnLineTime + '\'' +
                ", online=" + online +
                '}';
    }
}
