package com.example.mande.newkhanapos.models;

public class Tax {

    private int id;
    private int modifiedTime;
    private String name;
    private double rate;
    private String uniqueId;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getModifiedTime(){ return modifiedTime;}
    public void setModifiedTime(int modifiedTime){ this.modifiedTime = modifiedTime;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }
}