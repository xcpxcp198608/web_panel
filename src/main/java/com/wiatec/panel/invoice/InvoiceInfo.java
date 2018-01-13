package com.wiatec.panel.invoice;


/**
 * @author patrick
 */
public class InvoiceInfo {

    private String item;
    private String description;
    private int qty;
    private float price;
    private float amount;
    private boolean isTax;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public boolean isTax() {
        return isTax;
    }

    public void setTax(boolean tax) {
        isTax = tax;
    }

    @Override
    public String toString() {
        return "InvoiceInfo{" +
                "item='" + item + '\'' +
                ", description='" + description + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                ", amount=" + amount +
                ", isTax=" + isTax +
                '}';
    }


}
