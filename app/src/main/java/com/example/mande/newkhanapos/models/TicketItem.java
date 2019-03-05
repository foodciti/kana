package com.example.mande.newkhanapos.models;

import java.util.List;

public class TicketItem {

    private Integer id;

    private int itemId;

    private int maximumExtras;

    private int itemCount;

    private int splitItemCount;

    private String name;

    private String groupName;

    private String tableNumber;

    private String categoryName;

    private double unitPrice;

    private String menuItemId;

    private double taxRate;

    private double subtotalAmount;

    private double subtotalAmountWithoutModifiers;

    private double taxAmount;

    private double taxAmountWithoutModifiers;

    private double totalAmount;

    private double totalAmountWithoutModifiers;

    private boolean beverage;

    private boolean shouldPrintToKitchen;

    private boolean hasModifiers;

    private boolean printedToKitchen;

    private int printorder;

    private int bon;

    private int itemCountDisplay;

    private double unitPriceDisplay;

    private int itemCode;

    private Long modifiedTime;

    private String nameDisplay;

    private double totalAmountWithoutModifiersDisplay;

    private double taxAmountWithoutModifiersDisplay;

    private Ticket ticket;

    private List<TicketItemModifierGroup> ticketItemModifierGroups;

    private List<TicketItemCookingInstruction> cookingInstructions;

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

    public int getMaximumExtras() {
        return maximumExtras;
    }

    public void setMaximumExtras(int maximumExtras) {
        this.maximumExtras = maximumExtras;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(String menuItemId) {
        this.menuItemId = menuItemId;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public double getSubtotalAmount() {
        return subtotalAmount;
    }

    public void setSubtotalAmount(double subtotalAmount) {
        this.subtotalAmount = subtotalAmount;
    }

    public double getSubtotalAmountWithoutModifiers() {
        return subtotalAmountWithoutModifiers;
    }

    public void setSubtotalAmountWithoutModifiers(double subtotalAmountWithoutModifiers) {
        this.subtotalAmountWithoutModifiers = subtotalAmountWithoutModifiers;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getTaxAmountWithoutModifiers() {
        return taxAmountWithoutModifiers;
    }

    public void setTaxAmountWithoutModifiers(double taxAmountWithoutModifiers) {
        this.taxAmountWithoutModifiers = taxAmountWithoutModifiers;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getTotalAmountWithoutModifiers() {
        return totalAmountWithoutModifiers;
    }

    public void setTotalAmountWithoutModifiers(double totalAmountWithoutModifiers) {
        this.totalAmountWithoutModifiers = totalAmountWithoutModifiers;
    }

    public boolean isBeverage() {
        return beverage;
    }

    public void setBeverage(boolean beverage) {
        this.beverage = beverage;
    }

    public boolean isShouldPrintToKitchen() {
        return shouldPrintToKitchen;
    }

    public void setShouldPrintToKitchen(boolean shouldPrintToKitchen) {
        this.shouldPrintToKitchen = shouldPrintToKitchen;
    }

    public boolean isHasModifiers() {
        return hasModifiers;
    }

    public void setHasModifiers(boolean hasModifiers) {
        this.hasModifiers = hasModifiers;
    }

    public boolean isPrintedToKitchen() {
        return printedToKitchen;
    }

    public void setPrintedToKitchen(boolean printedToKitchen) {
        this.printedToKitchen = printedToKitchen;
    }

    public int getPrintorder() {
        return printorder;
    }

    public void setPrintorder(int printorder) {
        this.printorder = printorder;
    }

    public int getBon() {
        return bon;
    }

    public void setBon(int bon) {
        this.bon = bon;
    }

    public int getItemCountDisplay() {
        return itemCountDisplay;
    }

    public void setItemCountDisplay(int itemCountDisplay) {
        this.itemCountDisplay = itemCountDisplay;
    }

    public double getUnitPriceDisplay() {
        return unitPriceDisplay;
    }

    public void setUnitPriceDisplay(double unitPriceDisplay) {
        this.unitPriceDisplay = unitPriceDisplay;
    }

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public String getNameDisplay() {
        return nameDisplay;
    }

    public void setNameDisplay(String nameDisplay) {
        this.nameDisplay = nameDisplay;
    }

    public double getTotalAmountWithoutModifiersDisplay() {
        return totalAmountWithoutModifiersDisplay;
    }

    public void setTotalAmountWithoutModifiersDisplay(double totalAmountWithoutModifiersDisplay) {
        this.totalAmountWithoutModifiersDisplay = totalAmountWithoutModifiersDisplay;
    }

    public double getTaxAmountWithoutModifiersDisplay() {
        return taxAmountWithoutModifiersDisplay;
    }

    public void setTaxAmountWithoutModifiersDisplay(double taxAmountWithoutModifiersDisplay) {
        this.taxAmountWithoutModifiersDisplay = taxAmountWithoutModifiersDisplay;
    }

    public List<TicketItemModifierGroup> getTicketItemModifierGroups() {
        return ticketItemModifierGroups;
    }

    public void setTicketItemModifierGroups(List<TicketItemModifierGroup> ticketItemModifierGroups) {
        this.ticketItemModifierGroups = ticketItemModifierGroups;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getSplitItemCount() {
        return splitItemCount;
    }

    public void setSplitItemCount(int splitItemCount) {
        this.splitItemCount = splitItemCount;
    }

    public List<TicketItemCookingInstruction> getCookingInstructions() {
        return cookingInstructions;
    }

    public void setCookingInstructions(List<TicketItemCookingInstruction> cookingInstructions) {
        this.cookingInstructions = cookingInstructions;
    }
}
