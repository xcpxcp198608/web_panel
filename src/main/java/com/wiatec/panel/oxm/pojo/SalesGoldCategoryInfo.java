package com.wiatec.panel.oxm.pojo;


/**
 * @author patrick
 */
public class SalesGoldCategoryInfo {


    public static final String CATEGORY_NORMAL = "normal";
    public static final String CATEGORY_S1 = "S1";

    private int id;
    private String category;
    private float price;
    private int qty;
    private float amount;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public float getAmount() {
        return this.price * this.qty;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SalesGoldCategoryInfo{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", qty=" + qty +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                '}';
    }
}
