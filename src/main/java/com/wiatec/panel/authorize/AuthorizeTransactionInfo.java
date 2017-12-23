package com.wiatec.panel.authorize;

public class AuthorizeTransactionInfo {

    public static final String TYPE_CONTRACTED = "contracted";
    public static final String TYPE_MONTHLY = "monthly";
    public static final String TYPE_RENEW = "renew";


    private int id;
    private int salesId;
    private String salesName;
    private String category;
    private String clientKey;
    private String cardNumber;
    private String expirationDate;
    private String securityKey;

    private float amount;
    private float deposit;
    private float ldCommission;
    private float dealerCommission;
    private float salesCommission;
    private float txFee;

    private String transactionId;
    private String status;
    private String authCode;
    private String type;
    private String createTime;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
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

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
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

    public float getTxFee() {
        return txFee;
    }

    public void setTxFee(float txFee) {
        this.txFee = txFee;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "AuthorizeTransactionInfo{" +
                "id=" + id +
                ", salesId=" + salesId +
                ", salesName='" + salesName + '\'' +
                ", category='" + category + '\'' +
                ", clientKey='" + clientKey + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", expirationDate=" + expirationDate +
                ", securityKey=" + securityKey +
                ", amount=" + amount +
                ", deposit=" + deposit +
                ", ldCommission=" + ldCommission +
                ", dealerCommission=" + dealerCommission +
                ", salesCommission=" + salesCommission +
                ", txFee=" + txFee +
                ", transactionId='" + transactionId + '\'' +
                ", status='" + status + '\'' +
                ", authCode='" + authCode + '\'' +
                ", type='" + type + '\'' +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
