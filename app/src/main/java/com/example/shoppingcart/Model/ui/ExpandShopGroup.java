package com.example.shoppingcart.Model.ui;

import com.example.shoppingcart.Model.common.ShopItem;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ExpandShopGroup implements Comparable<ExpandShopGroup>, Serializable {
    private String shoppingId;
    private String date;
    private String name;
    private ArrayList<ShopItem> Items;
    @Override
    public int compareTo(ExpandShopGroup expandShopGroup) {
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ssZ");
        try{
            return sdf.parse(getDate()).compareTo(sdf.parse(expandShopGroup.getDate()));
        }
        catch (ParseException e){
            return 1;
        }
    }

    public ExpandShopGroup() {
    }

    public ExpandShopGroup(String shoppingId, String date, String name, ArrayList<ShopItem> items) {
        this.shoppingId = shoppingId;
        this.date = date;
        this.name = name;
        Items = items;
    }

    public String getShoppingId() {
        return shoppingId;
    }

    public void setShoppingId(String shoppingId) {
        this.shoppingId = shoppingId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ShopItem> getItems() {
        return Items;
    }

    public void setItems(ArrayList<ShopItem> items) {
        Items = items;
    }
}
