package com.example.shoppingcart.Model.common;

public class ShopItem {
    String category;
    String product;
    int quantity;
    String unit;

    public ShopItem() {
    }

    public ShopItem(String category, String product, int quantity, String unit) {
        this.category = category;
        this.product = product;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
