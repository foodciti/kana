package com.example.mande.newkhanapos.models;

public class TicketItemModifier {

    private Integer id;

    private int itemId;

    private int groupId;

    private int itemCount;

    private String name;

    private double unitPrice;

    private double extraUnitPrice;

    private double taxRate;

    private int modifierType;

    private java.lang.Double subTotalAmount;

    private java.lang.Double totalAmount;

    private java.lang.Double taxAmount;

    private java.lang.Boolean shouldPrintToKitchen;

    private java.lang.Boolean printedToKitchen;

    private java.lang.Integer printorder;

    private TicketItemModifierGroup parent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getExtraUnitPrice() {
        return extraUnitPrice;
    }

    public void setExtraUnitPrice(double extraUnitPrice) {
        this.extraUnitPrice = extraUnitPrice;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public int getModifierType() {
        return modifierType;
    }

    public void setModifierType(int modifierType) {
        this.modifierType = modifierType;
    }

    public Double getSubTotalAmount() {
        return subTotalAmount;
    }

    public void setSubTotalAmount(Double subTotalAmount) {
        this.subTotalAmount = subTotalAmount;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(Double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public Boolean getShouldPrintToKitchen() {
        return shouldPrintToKitchen;
    }

    public void setShouldPrintToKitchen(Boolean shouldPrintToKitchen) {
        this.shouldPrintToKitchen = shouldPrintToKitchen;
    }

    public Boolean getPrintedToKitchen() {
        return printedToKitchen;
    }

    public void setPrintedToKitchen(Boolean printedToKitchen) {
        this.printedToKitchen = printedToKitchen;
    }

    public Integer getPrintorder() {
        return printorder;
    }

    public void setPrintorder(Integer printorder) {
        this.printorder = printorder;
    }

    public TicketItemModifierGroup getParent() {
        return parent;
    }

    public void setParent(TicketItemModifierGroup parent) {
        this.parent = parent;
    }
}

