package com.wiatec.panel.paypal;

public class PayOrderInfo {

    private int id;
    private String invoice;
    private String category;
    private float price;
    private float txFee;
    private String currency;
    private String paymentStatus;
    private int salesId;
    private String clientKey;
    private String description;

    private String transactionId;
    private String payerId;
    private String payerEmail;
    private String receiverId;
    private String verifySign;
    private String paymentDate;
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getTxFee() {
        return txFee;
    }

    public void setTxFee(float txFee) {
        this.txFee = txFee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getSalesId() {
        return salesId;
    }

    public void setSalesId(int salesId) {
        this.salesId = salesId;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getPayerId() {
        return payerId;
    }

    public void setPayerId(String payerId) {
        this.payerId = payerId;
    }

    public String getPayerEmail() {
        return payerEmail;
    }

    public void setPayerEmail(String payerEmail) {
        this.payerEmail = payerEmail;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getVerifySign() {
        return verifySign;
    }

    public void setVerifySign(String verifySign) {
        this.verifySign = verifySign;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "PayOrderInfo{" +
                "id=" + id +
                ", invoice='" + invoice + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", txFee=" + txFee +
                ", currency='" + currency + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", salesId=" + salesId +
                ", clientKey='" + clientKey + '\'' +
                ", description='" + description + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", payerId='" + payerId + '\'' +
                ", payerEmail='" + payerEmail + '\'' +
                ", receiverId='" + receiverId + '\'' +
                ", verifySign='" + verifySign + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
