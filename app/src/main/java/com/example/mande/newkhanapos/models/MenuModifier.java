package com.example.mande.newkhanapos.models;

public class MenuModifier {

    private int id;

    private int modifierId;

    private String name;

    private double price;

    private double extraPrice;

    private Tax tax;

    private int modifierCount;

    private EnumUtil.TicketItemModifierType modifierType;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getModifierId() {
        return modifierId;
    }

    public void setModifierId(int modifierId) {
        this.modifierId = modifierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(double extraPrice) {
        this.extraPrice = extraPrice;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public int getModifierCount() {
        return modifierCount;
    }

    public void setModifierCount(int modifierCount) {
        this.modifierCount = modifierCount;
    }


    public EnumUtil.TicketItemModifierType getModifierType() {
        return modifierType != null ? modifierType : EnumUtil.TicketItemModifierType.NORMAL_MODIFIER;
    }

    public void setModifierType(EnumUtil.TicketItemModifierType modifierType) {
        this.modifierType = modifierType;
    }
}
