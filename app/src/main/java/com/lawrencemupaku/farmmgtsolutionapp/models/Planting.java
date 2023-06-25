package com.lawrencemupaku.farmmgtsolutionapp.models;

import java.io.Serializable;

public class Planting implements Serializable {
    private String date;
    private String mechanicalPlanting;
    private String manualPlanting;
    private String transPlanting;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMechanicalPlanting() {
        return mechanicalPlanting;
    }

    public void setMechanicalPlanting(String mechanicalPlanting) {
        this.mechanicalPlanting = mechanicalPlanting;
    }

    public String getManualPlanting() {
        return manualPlanting;
    }

    public void setManualPlanting(String manualPlanting) {
        this.manualPlanting = manualPlanting;
    }

    public String getTransPlanting() {
        return transPlanting;
    }

    public void setTransPlanting(String transPlanting) {
        this.transPlanting = transPlanting;
    }
}
