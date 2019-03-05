package com.example.mande.newkhanapos.models;

import com.example.mande.newkhanapos.MainActivity;

public class Article {

    private int id;
    private String name;
    private String itemId;
    private String subitemid;
    private double price;
    private double weight;
    private int gaenge;
    private boolean beverage;
    private boolean changeprice;
    private Tax tax;
    public MenuItemModifierGroup menuItemModiferGroups;
    private MenuItemPrice menuitemprice;
    private String uniqueId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getSubitemid() {
        return subitemid;
    }

    public void setSubitemid(String subitemid) {
        this.subitemid = subitemid;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getGaenge() {
        return gaenge;
    }

    public void setGaenge(int gaenge) {
        this.gaenge = gaenge;
    }

    public boolean isBeverage() {
        return beverage;
    }

    public void setBeverage(boolean beverage) {
        this.beverage = beverage;
    }

    public boolean isChangeprice() {
        return changeprice;
    }

    public void setChangeprice(boolean changeprice) {
        this.changeprice = changeprice;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public MenuItemModifierGroup getMenuItemModiferGroups() {
        return menuItemModiferGroups;
    }

    public void setMenuItemModiferGroups(MenuItemModifierGroup menuItemModiferGroups) {
        this.menuItemModiferGroups = menuItemModiferGroups;
    }

    public MenuItemPrice getMenuitemprice() {
        return menuitemprice;
    }

    public void setMenuitemprice(MenuItemPrice menuitemprice) {
        this.menuitemprice = menuitemprice;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}
