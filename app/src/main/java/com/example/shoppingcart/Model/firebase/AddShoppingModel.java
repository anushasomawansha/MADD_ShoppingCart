package com.example.shoppingcart.Model.firebase;

import com.example.shoppingcart.Model.common.ShopItem;

import java.util.Map;

public class AddShoppingModel {
    Map<String, ShopItem> sales;
    String date;

    public AddShoppingModel() {
    }

    public AddShoppingModel(Map<String, ShopItem> sales, String date) {
        this.sales = sales;
        this.date = date;
    }

    public Map<String, ShopItem> getSales() {
        return sales;
    }

    public void setSales(Map<String, ShopItem> sales) {
        this.sales = sales;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
