package com.example.mande.newkhanapos.models;

import java.util.ArrayList;
import java.util.List;

public class TicketItemModifierGroup {

    private Integer id;

    private int modifierGroupId;

    private int minQuantity;

    private int maxQuantity;

    private TicketItem parent;

    private java.util.List<TicketItemModifier> ticketItemModifiers;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getModifierGroupId() {
        return modifierGroupId;
    }

    public void setModifierGroupId(int modifierGroupId) {
        this.modifierGroupId = modifierGroupId;
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

    public List<TicketItemModifier> getTicketItemModifiers() {
        return ticketItemModifiers != null ? ticketItemModifiers : new ArrayList<TicketItemModifier>();
    }

    public void setTicketItemModifiers(List<TicketItemModifier> ticketItemModifiers) {
        this.ticketItemModifiers = ticketItemModifiers;
    }

    public TicketItem getParent() {
        return parent;
    }

    public void setParent(TicketItem parent) {
        this.parent = parent;
    }
}
