package com.example.mande.newkhanapos.models;

public class Table {

    private int id;

    private String number;

    private int floor;

    private boolean occupied;

    private String tickettype;

    private double tableamount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public String getTickettype() {
        return tickettype;
    }

    public void setTickettype(String tickettype) {
        this.tickettype = tickettype;
    }

    public double getAmount(){ return tableamount;}

    public void setAmount(double tableamount){this.tableamount = tableamount; }

}
