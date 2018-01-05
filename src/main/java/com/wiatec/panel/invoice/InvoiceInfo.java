package com.wiatec.panel.invoice;

import java.util.ArrayList;
import java.util.List;

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

    public static InvoiceInfo createDeposit(){
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setItem("SD001");
        invoiceInfo.setDescription("Security Deposit");
        invoiceInfo.setQty(1);
        invoiceInfo.setPrice(100);
        invoiceInfo.setAmount(100);
        return invoiceInfo;
    }

    public static InvoiceInfo createB1(){
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setItem("BVB1");
        invoiceInfo.setDescription("BVision Basic 24 months");
        invoiceInfo.setQty(1);
        invoiceInfo.setPrice(19.99f);
        invoiceInfo.setAmount(19.99f);
        invoiceInfo.setTax(true);
        return invoiceInfo;
    }

    public static InvoiceInfo createP1(){
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setItem("BVP1");
        invoiceInfo.setDescription("BVision VIP 12 months");
        invoiceInfo.setQty(1);
        invoiceInfo.setPrice(34.99F);
        invoiceInfo.setAmount(34.99F);
        invoiceInfo.setTax(true);
        return invoiceInfo;
    }

    public static InvoiceInfo createP2(){
        InvoiceInfo invoiceInfo = new InvoiceInfo();
        invoiceInfo.setItem("BVP2");
        invoiceInfo.setDescription("BVision VIP 24 months");
        invoiceInfo.setQty(1);
        invoiceInfo.setPrice(29.99F);
        invoiceInfo.setAmount(29.99F);
        invoiceInfo.setTax(true);
        return invoiceInfo;
    }

    public static List<InvoiceInfo> B1Contracted(){
        List<InvoiceInfo> invoiceInfoList = new ArrayList<>();
        invoiceInfoList.add(createDeposit());
        invoiceInfoList.add(createB1());
        return invoiceInfoList;
    }

    public static List<InvoiceInfo> P1Contracted(){
        List<InvoiceInfo> invoiceInfoList = new ArrayList<>();
        invoiceInfoList.add(createDeposit());
        invoiceInfoList.add(createP1());
        return invoiceInfoList;
    }

    public static List<InvoiceInfo> P2Contracted(){
        List<InvoiceInfo> invoiceInfoList = new ArrayList<>();
        invoiceInfoList.add(createDeposit());
        invoiceInfoList.add(createP2());
        return invoiceInfoList;
    }

    public static List<InvoiceInfo> B1Monthly(){
        List<InvoiceInfo> invoiceInfoList = new ArrayList<>();
        invoiceInfoList.add(createB1());
        return invoiceInfoList;
    }

    public static List<InvoiceInfo> P1Monthly(){
        List<InvoiceInfo> invoiceInfoList = new ArrayList<>();
        invoiceInfoList.add(createP1());
        return invoiceInfoList;
    }

    public static List<InvoiceInfo> P2Monthly(){
        List<InvoiceInfo> invoiceInfoList = new ArrayList<>();
        invoiceInfoList.add(createP2());
        return invoiceInfoList;
    }
}
