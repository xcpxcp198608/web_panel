package com.wiatec.panel.oxm.pojo;


/**
 * @author patrick
 */
public class SalesActivateCategoryInfo {

    public static final String CATEGORY_AM1 = "AM1";
    public static final String CATEGORY_AM2 = "AM2";

    private int id;
    private String category;
    private float price;
    private int month;
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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SalesActivateCategoryInfo{" +
                "id=" + id +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", month=" + month +
                ", description='" + description + '\'' +
                '}';
    }
}
