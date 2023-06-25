package com.lawrencemupaku.farmmgtsolutionapp.models;

import java.io.Serializable;

public class WeedingProgram implements Serializable {
    private String date;
    private String herbicides;
    private String manualRemoval;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHerbicides() {
        return herbicides;
    }

    public void setHerbicides(String herbicides) {
        this.herbicides = herbicides;
    }

    public String getManualRemoval() {
        return manualRemoval;
    }

    public void setManualRemoval(String manualRemoval) {
        this.manualRemoval = manualRemoval;
    }
}
