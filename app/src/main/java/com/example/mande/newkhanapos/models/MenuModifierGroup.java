package com.example.mande.newkhanapos.models;

import java.util.Set;

public class MenuModifierGroup {

    private int id;

    private String name;

    public Set<MenuModifier> modifiers;

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

    public Set<MenuModifier> getModifiers() {
        return modifiers;
    }

    public void setModifiers(Set<MenuModifier> modifiers) {
        this.modifiers = modifiers;
    }
}

