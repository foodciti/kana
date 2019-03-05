package com.example.mande.newkhanapos.models;

public class MenuItemModifierGroup {

    private int id;

    private int minQuantity;

    private int maxQuantity;

    public MenuModifierGroup modifierGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public int getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(int maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public MenuModifierGroup getModifierGroup() {
        return modifierGroup;
    }

    public void setModifierGroup(MenuModifierGroup modifierGroup) {
        this.modifierGroup = modifierGroup;
    }
}

