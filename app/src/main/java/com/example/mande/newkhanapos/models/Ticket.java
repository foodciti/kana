package com.example.mande.newkhanapos.models;

import java.util.List;

public class Ticket {

    private int id;

    private boolean paid;

    private double taxAmount;

    private double totalAmount;

    private boolean cashPayment;

    private User owner;

    protected long createDate;

    private Long modifiedTime;

    public List<TicketItem> ticketItems;

    //private List<ShopTable> tables;

    private String tableNumbers;

    private boolean drawerResetted;

    private boolean reOpened;

    private String ticketType;

    private boolean split;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public double getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(double taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public boolean isCashPayment() {
        return cashPayment;
    }

    public void setCashPayment(boolean cashPayment) {
        this.cashPayment = cashPayment;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<TicketItem> getTicketItems() {
        return ticketItems;
    }

    public void setTicketItems(List<TicketItem> ticketItems) {
        this.ticketItems = ticketItems;
    }

   // public List<ShopTable> getTables() {
    //    return tables;
    //}

    //public void setTables(List<ShopTable> tables) {
    //    this.tables = tables;
    //}

    public String getTableNumbers() {
        return tableNumbers;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public void setTableNumbers(String tableNumbers) {
        this.tableNumbers = tableNumbers;
    }

    public boolean isDrawerResetted() {
        return drawerResetted;
    }

    public void setDrawerResetted(boolean drawerResetted) {
        this.drawerResetted = drawerResetted;
    }

    public boolean isReOpened() {
        return reOpened;
    }

    public void setReOpened(boolean reOpened) {
        this.reOpened = reOpened;
    }

    public boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public Long getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

}
