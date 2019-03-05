package com.example.mande.newkhanapos.models;

public class EnumUtil {
    public enum TicketSaveType {
        SALDO,
        CLOSE,
        ZWS
    }

    public enum PaymentType {
        CASH,
        CARD,
        ONLINE
    }

    public enum TicketState {
        OPEN,
        CLOSED,
        SPLITTED,
        SALES_REPORT_TAKEN,
        SALES_REPORT_NOT_TAKEN,
        PAID,
        DELETED,
        DINE_IN,
        HOME_DELIVERY
    }

    public enum TicketItemModifierType {
        MODIFIER_NOT_INITIALIZED,
        NORMAL_MODIFIER,
        NO_MODIFIER,
        EXTRA_MODIFIER
    }
}
