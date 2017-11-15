package com.wiatec.panel.paypal;

public class PayInfo {

    private String invoice;
    private String itemName;
    private String itemNumber;
    private float amount;
    private float tax;
    private String currency;

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "PayInfo{" +
                "invoice='" + invoice + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemNumber='" + itemNumber + '\'' +
                ", amount=" + amount +
                ", tax=" + tax +
                ", currency='" + currency + '\'' +
                '}';
    }
}
