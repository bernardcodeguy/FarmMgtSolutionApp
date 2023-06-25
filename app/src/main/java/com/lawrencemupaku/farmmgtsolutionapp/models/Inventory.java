package com.lawrencemupaku.farmmgtsolutionapp.models;

import java.io.Serializable;

public class Inventory implements Serializable {
    private String date;
    private String closingInventory;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClosingInventory() {
        return closingInventory;
    }

    public void setClosingInventory(String closingInventory) {
        this.closingInventory = closingInventory;
    }
}
